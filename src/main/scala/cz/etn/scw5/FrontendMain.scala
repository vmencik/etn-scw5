package cz.etn.scw5

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import akka.actor.Props
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.concurrent.Future
import akka.actor.ActorRef
import scala.concurrent.ExecutionContext.Implicits.global

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

  val selection = system.actorSelection(address)

  val fExchange: Future[ActorRef] = selection.resolveOne(3 seconds)

  fExchange onSuccess {
    case exchange =>
      val tr1 = system.actorOf(Props(classOf[Trader], exchange, "richTrader"), "first")
      val tr2 = system.actorOf(Props(new Trader(exchange, "poorTrader")), "second")

      val inputWindow = new TraderInput(exchange, tr1)
      val inputWindow2 = new TraderInput(exchange, tr2)
  }

  fExchange onFailure {
    case ex =>
      ex.printStackTrace()
      system.shutdown
  }

}