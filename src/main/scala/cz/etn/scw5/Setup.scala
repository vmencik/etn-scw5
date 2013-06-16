package cz.etn.scw5

import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.actorRef2Scala

object Setup extends App {

  val system = ActorSystem("exchange")
  val exchange = system.actorOf(Props(new Exchange))
  val tr1 = system.actorOf(Props(new Trader(exchange)), "first")
  //  val tr2 = system.actorOf(Props(new Trader(exchange)), "second")
  //  val tr3 = system.actorOf(Props(new Trader(exchange)), "third")

  val buy = Buy("gold", quantity = 10, price = 500)
  val sell = Sell("gold", quantity = 10, price = 4)

  exchange.tell(buy, tr1)
  exchange ! sell

  Thread.sleep(5000)
  system.shutdown
}