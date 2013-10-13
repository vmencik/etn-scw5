name := "etn-scw5"

version := "1.0-SNAPSHOT"

scalaVersion := "2.10.2"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq (
	"com.typesafe.akka" %% "akka-actor" % "2.2.1",
	"org.scalatest" %% "scalatest" % "2.0.RC1" % "test",
	"com.typesafe.akka" %% "akka-testkit" % "2.2.1" % "test"
)

retrieveManaged := true

EclipseKeys.relativizeLibs := true

