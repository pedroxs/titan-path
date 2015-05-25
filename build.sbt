name := "titan-path"

version := "0.1.0-SNAPSHOT"

lazy val `titan-path` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq( jdbc , anorm , cache , ws )

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

val versions = new {
  val titan = "0.9.0-M1"
  val gremlinScala = "3.0.0.M6c"
}

libraryDependencies ++= Seq(
  "com.thinkaurelius.titan" % "titan-core" % versions.titan,
  "com.thinkaurelius.titan" % "titan-cassandra" % versions.titan,
  "com.thinkaurelius.titan" % "titan-es" % versions.titan,
  "com.michaelpollmeier" %% "gremlin-scala" % versions.gremlinScala
)

scalacOptions in Test ++= Seq("-Yrangepos")