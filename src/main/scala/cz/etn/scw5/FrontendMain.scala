package cz.etn.scw5

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import akka.actor.Props

object FrontendMain extends App {

  val config = ConfigFactory.load("frontend")
  val system = ActorSystem("frontend", config)

  val address =
    {
      val backendConfig = config.getConfig("backend")
      val host = backendConfig.getString("host")
      val port = backendConfig.getInt("port")
      val protocol = backendConfig.getString("protocol")
      val backendSystem = backendConfig.getString("system")
      val exchange = backendConfig.getString("exchange")
      s"$protocol://$backendSystem@$host:$port/$exchange"
    }

}