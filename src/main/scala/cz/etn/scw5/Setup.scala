package cz.etn.scw5

import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.actorRef2Scala

object Setup extends App {

  val system = ActorSystem("exchange")
  val exchange = system.actorOf(Props(new Exchange))
  val tr1 = system.actorOf(Props(new Trader(exchange)), "first")
  val tr2 = system.actorOf(Props(new Trader(exchange)), "second")
  val tr3 = system.actorOf(Props(new Trader(exchange)), "third")
  exchange ! Subscribe(tr1)
  exchange ! Subscribe(tr2)
  exchange ! Subscribe(tr3)

  val buy = Buy("gold", quantity = 10, price = 5)
  val sell = Sell("gold", quantity = 10, price = 4)

  exchange ! buy
  exchange ! sell

  Thread.sleep(2000)
  system.shutdown
}