package cz.etn.scw5

import akka.actor.ActorSystem
import akka.actor.Props

object ExchangeWork {
  Seq("Ahoj")                                     //> res0: Seq[String] = List(Ahoj)
  val a = List(2,2,2,3,4,5,6)                     //> a  : List[Int] = List(2, 2, 2, 3, 4, 5, 6)
  
  val (prefix, suffix) = a.span(_ != 4)           //> prefix  : List[Int] = List(2, 2, 2, 3)
                                                  //| suffix  : List[Int] = List(4, 5, 6)
  val system = ActorSystem("exchange")            //> system  : akka.actor.ActorSystem = akka://exchange
  val exchange = system.actorOf(Props(new Exchange))
                                                  //> exchange  : akka.actor.ActorRef = Actor[akka://exchange/user/$a]
  val tr1 = system.actorOf(Props(new Trader(exchange)), "first")
                                                  //> tr1  : akka.actor.ActorRef = Actor[akka://exchange/user/first]
  val tr2 = system.actorOf(Props(new Trader(exchange)), "second")
                                                  //> tr2  : akka.actor.ActorRef = Actor[akka://exchange/user/second]
  val tr3 = system.actorOf(Props(new Trader(exchange)), "third")
                                                  //> tr3  : akka.actor.ActorRef = Actor[akka://exchange/user/third]
  exchange ! Subscribe(tr1)
  exchange ! Subscribe(tr2)
  exchange ! Subscribe(tr3)
  
  val buy = Buy("gold", quantity = 10, price = 5) //> buy  : cz.etn.scw5.Buy = Buy(gold,10,5)
  val sell = Sell("gold", quantity = 10, price = 4)
                                                  //> sell  : cz.etn.scw5.Sell = Sell(gold,10,4)
  
  exchange ! buy
  exchange ! sell
  
  Thread.sleep(2000)                              //> exchange-akka.actor.default-dispatcher-6
                                                  //| exchange-akka.actor.default-dispatcher-6
                                                  //| first: Trade(gold,10,5)
                                                  //| third: Trade(gold,10,5)
                                                  //| second: Trade(gold,10,5)-
  system.shutdown
  
}