name := "etn-scw5"

version := "1.0-SNAPSHOT"

scalaVersion := "2.10.1"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq (
	"com.typesafe.akka" %% "akka-actor" % "2.1.4",
	"org.scalatest" %% "scalatest" % "2.0.M5b" % "test",
	"com.typesafe.akka" %% "akka-testkit" % "2.1.4" % "test"
)

retrieveManaged := true

EclipseKeys.relativizeLibs := true

