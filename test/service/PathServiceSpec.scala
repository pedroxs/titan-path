package service

import model.{TraceRequest, Path}
import org.specs2.mutable.Specification

/**
 * Created by Pedro on 25/05/2015.
 */
class PathServiceSpec extends Specification with PathService {

  "PathService" should {
    "add a location" in {
      val result = addLocation("SP", Path("Sao Paulo", "Maringa", 120))
      result._1 should not(beNull)
      result._2 should not(beNull)
    }

    "trace a route" in {
      addLocation("SP", Path("Sao Paulo", "Maringa", 120))
      addLocation("SP", Path("Maringa", "Marilia", 160))

      val result = traceRoute("SP", TraceRequest("Sao Paulo", "Marilia", 50, 2.8))
      println(result)
      result must not(beNull)
      result.route must beEqualTo("Sao Paulo -> Maringa -> Marilia")
      result.distance must beEqualTo(280)
      result.cost must beEqualTo(14.0)
    }
  }
}
