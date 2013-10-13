package cz.etn.scw5

import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.actorRef2Scala

object Setup extends App {

  val system = ActorSystem("exchange")
  val exchange = system.actorOf(Props[Exchange])
  val tr1 = system.actorOf(Props(classOf[Trader], exchange), "first")
  val tr2 = system.actorOf(Props(new Trader(exchange)), "second")
  //  val tr3 = system.actorOf(Props(new Trader(exchange)), "third")

  val buy = Buy("gold", quantity = 10, price = 5)
  val sell = Sell("gold", quantity = 10, price = 4)

  Thread.sleep(1000)

  exchange.tell(buy, tr1)
  exchange.tell(sell, tr2)
  //  exchange ! sell

  Thread.sleep(5000)
  system.shutdown
}