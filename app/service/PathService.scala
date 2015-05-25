package service

import com.tinkerpop.gremlin.process.Traverser
import com.tinkerpop.gremlin.scala.ScalaVertex
import com.tinkerpop.gremlin.structure.{Edge, Vertex}
import conf.TitanConf
import model.{Path, TraceRequest, TraceResult}

import scala.collection.JavaConversions._

/**
 * Created by Pedro on 24/05/2015.
 */
trait PathService extends TitanConf {

  def addLocation(map:String, path: Path) = {
    execute { g =>
      val origin = g.V.has(map, "name", path.origin).headOption()
      val destination = g.V.has(map, "name", path.destination).headOption()
      addPath(
        origin.getOrElse(g.addVertex(map).setProperty("name", path.origin).element),
        destination.getOrElse(g.addVertex(map).setProperty("name", path.destination).element),
        path.distance
      )
    }
  }

  private def addPath(from: Vertex, to: Vertex, distance: Int) = {
    execute { g =>
      val svFrom = ScalaVertex.apply(from)
      val svTo = ScalaVertex.apply(to)
      svFrom.addEdge(label = "path", to, Map("distance" -> distance))
      svTo.addEdge(label = "path", from, Map("distance" -> distance))
      (svFrom, svTo)
    }
  }

  def traceRoute(map: String, traceRequest: TraceRequest): TraceResult = {
    execute { g =>
      val origin = g.V.has(map, "name", traceRequest.origin).headOption()
      origin match {
        case None => throw new Exception("Origin not found in map.")
        case Some(o) => {
          val paths = ScalaVertex.apply(o).as("o").outE.inV.jump(
            to = "o",
            jumpPredicate = { t: Traverser[Vertex] =>
              t.loops() < 6 &&
              t.get.value[String]("name") != traceRequest.origin &&
              t.get.value[String]("name") != traceRequest.destination
            }
          ).filter(_.value[String]("name") == traceRequest.destination).path.toList

          val traceResult: List[TraceResult] = paths map { path =>
            val pathDescriptions = path.objects collect {
              case v: Vertex => v.value[String]("name")
            } mkString " -> "

            val pathDistance = path.objects collect {
              case e: Edge => e.value[Int]("distance")
            } sum

            TraceResult(pathDescriptions, pathDistance, (pathDistance / traceRequest.autonomy) * traceRequest.price)
          }
          traceResult.sortBy(_.distance).head
        }
      }
    }
  }
}
