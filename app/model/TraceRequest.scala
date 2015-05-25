package model

import play.api.libs.json.Json

/**
 * Created by Pedro on 25/05/2015.
 */
case class TraceRequest (
  origin: String,
  destination: String,
  autonomy: Int,
  price: BigDecimal
)

object TraceRequest {
  implicit val format = Json.format[TraceRequest]
}
