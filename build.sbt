name := "swing-streams"

version := "0.0"

scalaVersion := "2.11.1"

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-unchecked",
  "-Xfatal-warnings",
  "-Xlint",
  "-Yno-adapted-args",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard"
)

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-concurrent" % "7.0.6",
  "org.scalaz" %% "scalaz-core" % "7.0.6",
  "org.scalaz.stream" %% "scalaz-stream" % "0.4.1"
)

resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"

scalariformSettings
