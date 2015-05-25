package conf

import play.api._

/**
 * Created by Pedro on 25/05/2015.
 */
object Global extends GlobalSettings with TitanConf {

  override def onStart(app: Application) {
    connect()
    Logger.info("Application has finished starting")
  }

  override def onStop(app: Application) {
    disconnect()
    Logger.info("Application shutdown...")
  }
}
