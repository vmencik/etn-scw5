name := "etn-scw5"

version := "1.0-SNAPSHOT"

scalaVersion := "2.10.2"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= {
	val akkaVersion = "2.2.1"
	Seq (
		"com.typesafe.akka" %% "akka-actor" % akkaVersion,
		"com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
		"ch.qos.logback" % "logback-classic" % "1.0.10",
		"org.scalatest" %% "scalatest" % "2.0.RC1" % "test",
		"com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test"
	)
}

retrieveManaged := true

EclipseKeys.relativizeLibs := true

EclipseKeys.projectFlavor := EclipseProjectFlavor.Scala

