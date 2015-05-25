package repository

import com.thinkaurelius.titan.core.TitanFactory
import com.tinkerpop.gremlin.scala.GremlinScala
import org.specs2.mutable._

/**
 * Created by Pedro on 24/05/2015.
 */
class TitanSpec extends Specification {

  "Titan DB" should {
    "initialize in memory" in {
      val graph = TitanFactory.build().set("storage.backend","inmemory").open()
      val gremlin = GremlinScala(graph)

      graph must not(beNull)
      gremlin must not(beNull)
      graph.close() must not(throwA[Exception])
    }

    "initialize with cassandra" in {
      val prop = getClass.getResource("/titan-cassandra.properties")
      val graph = TitanFactory.open(prop.getFile)
      val gremlin = GremlinScala(graph)

      graph must not(beNull)
      gremlin must not(beNull)
      graph.close() must not(throwA[Exception])
    }
  }
}
