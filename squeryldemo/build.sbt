
lazy val root = (project in file(".")).settings(
  organization := "edu.ciukstar",
  version := "0.1.0",
  scalaVersion := "2.11.8",
  name := "squeryldemo",
  libraryDependencies := Seq(
    "org.squeryl" %% "squeryl" % "0.9.7",
    "com.h2database" % "h2" % "1.4.192",
    "org.scalactic" %% "scalactic" % "3.0.0",
    "org.scalatest" %% "scalatest" % "3.0.0" % "test"
  )
)
