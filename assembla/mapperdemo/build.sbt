
lazy val commonSettings = Seq(
  organization := "edu.ciukstar",
  version := "0.1.0",
  scalaVersion := "2.11.8"
)

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "mapperdemo",
    libraryDependencies ++= {
      val liftVersion = "3.0-RC3"
      Seq(
        "net.liftweb" %% "lift-webkit" % liftVersion,
        "net.liftweb" %% "lift-mapper" % liftVersion,
        "com.h2database" % "h2" % "1.4.192",
        "org.slf4j" % "slf4j-simple" % "1.7.21" % "test",
        "org.scalactic" %% "scalactic" % "3.0.0",
        "org.scalatest" %% "scalatest" % "3.0.0" % "test"
      )
    }
  )
