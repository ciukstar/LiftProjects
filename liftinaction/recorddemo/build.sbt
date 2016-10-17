
lazy val root = (project in file(".")).settings(
  organization := "edu.ciukstar",
  version := "0.1.0",
  scalaVersion := "2.11.8",
  name := "recorddemo",
  parallelExecution in Test := false,
  libraryDependencies := {
    val liftVersion = "3.0-RC3"
    Seq(
      "net.liftweb" %% "lift-squeryl-record" % liftVersion,
      "net.liftweb" %% "lift-record" % liftVersion,
      "org.scalactic" %% "scalactic" % "3.0.0",
      "org.scalatest" %% "scalatest" % "3.0.0" % "test"
    )
  }
)
