package cz.etn.scw5

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import akka.actor.Props

object BackendMain extends App {

  val config = ConfigFactory.load("backend")
  val system = ActorSystem("backend", config)
  system.actorOf(Props[Exchange], "exchange")

}