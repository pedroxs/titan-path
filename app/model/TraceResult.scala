package model

import play.api.libs.json.Json

/**
 * Created by Pedro on 25/05/2015.
 */
case class TraceResult (
  route: String,
  distance: Int,
  cost: BigDecimal = 0
)

object TraceResult {
  implicit val format = Json.format[TraceResult]
}
