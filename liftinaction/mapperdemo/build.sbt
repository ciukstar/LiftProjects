
lazy val root = (project in file(".")).settings(
  organization := "edu.ciukstar",
  version := "0.1.0",
  scalaVersion := "2.11.8",
  name := "mapperdemo",
  parallelExecution in Test := false,
  libraryDependencies := {
    val liftVersion = "3.0-RC3"
    Seq(
      "net.liftweb" %% "lift-webkit" % liftVersion,
      "net.liftweb" %% "lift-mapper" % liftVersion,
      "org.slf4j" % "slf4j-simple" % "1.7.21",
      "com.h2database" % "h2" % "1.4.192",
      "org.scalactic" %% "scalactic" % "3.0.0",
      "org.scalatest" %% "scalatest" % "3.0.0" % "test"
    )
  }
)
