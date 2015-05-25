package controllers

import model.{Path, TraceRequest}
import play.api.libs.json.{JsError, Json}
import play.api.mvc._
import service.PathService

/**
 * Created by Pedro on 24/05/2015.
 */
object PathController extends Controller with PathService {

  def add(map: String) = Action(BodyParsers.parse.json) { request =>
    val result = request.body.validate[Path]
    result.fold(
      error => BadRequest(Json.obj("status" -> "Nok", "reason" -> JsError.toFlatJson(error))),
      path => {
        addLocation(map, path)
        Created
      }
    )
  }

  //TODO remove path so maps may be updated
  def remove = ???

  def trace(map: String) = Action(BodyParsers.parse.json) { request =>
    val result = request.body.validate[TraceRequest]
    result.fold(
      error => BadRequest(Json.obj("status" -> "Nok", "reason" -> JsError.toFlatJson(error))),
      trace => Ok(Json.toJson(traceRoute(map, trace)))
    )
  }
}
