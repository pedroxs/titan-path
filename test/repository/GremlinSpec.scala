package repository

import com.tinkerpop.gremlin.process.T
import conf.TitanConf
import org.specs2.mutable._

/**
 * Created by Pedro on 24/05/2015.
 */
class GremlinSpec extends Specification with TitanConf {

  "Gremlin" should {
    "add some property" in {
      execute { g =>
        val r = g.addVertex().setProperty("map", "Sao Paulo")
        println(s"addVertex $r")

        val v = g.V.has(T.value, "Sao Paulo")
//        println(s"has value ${v.headOption()}")

        val k = g.V.has(T.key, "Sao Paulo")
//        println(s"has key ${k.headOption()}")

        val l = g.V.has(T.label, "Sao Paulo")
        println(s"has label ${l.headOption()}")
      }
      true
    }
    "add some label" in {
      val r = execute { g =>
        g.addVertex("Sao Paulo", Map("map"->"Sao Paulo"))
      }
      println(s"add vertex outer scope $r")

      execute { g =>
        val q = g.V.traversal
        println(s"query $q")
      }
      true
    }
  }

  "find" should {
    "print some" in {
      execute { g =>
        g.V.map { v =>
          println(v)
        }
        val result = g.V.has(T.label, "Sao Paulo")
        println(s"has label $result")
        val v = result.head()
        println(s"result head $v")
        println(s"hummm ${v.toString()}")

        val c = g.V.value[String]("map")
        println(c.toList)
        true
      }
    }
  }
}
