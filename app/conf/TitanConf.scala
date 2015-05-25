package conf

import com.thinkaurelius.titan.core.{TitanGraph, TitanFactory}
import com.tinkerpop.gremlin.scala.{ScalaGraph, GremlinScala}
import play.api.Logger

/**
 * Created by Pedro on 24/05/2015.
 */
trait TitanConf {

  val prop = getClass.getResource("/titan-cassandra.properties")
  var graph: TitanGraph = null

  def connect() = {
    if(graph == null) graph = TitanFactory.open(prop.getFile)
    Logger.info(s"graph should not be null: $graph")
  }

  def disconnect() = {
    graph.close()
  }

  def execute[T](fn: (ScalaGraph) => T) = {
    if(graph == null) { Logger.info("had to reconnect"); connect()}
    val gremlin = GremlinScala(graph)
    fn(gremlin)
  }

}
