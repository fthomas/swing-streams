name := "swing-streams"

version := "0.0"

scalaVersion := "2.11.0"

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-concurrent" % "7.0.6",
  "org.scalaz.stream" %% "scalaz-stream" % "0.4.1"
)

resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"
