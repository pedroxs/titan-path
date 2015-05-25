package model

import play.api.libs.json.Json

/**
 * Created by Pedro on 24/05/2015.
 */
case class Path(
  origin: String,
  destination: String,
  distance: Int
)

object Path {
  implicit val format = Json.format[Path]
}