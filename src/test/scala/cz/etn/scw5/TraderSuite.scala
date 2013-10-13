package cz.etn.scw5

import akka.testkit.TestActorRef
import akka.actor.Props
import akka.actor.Kill
import akka.actor.PoisonPill
import akka.testkit.TestProbe
import com.typesafe.config.ConfigFactory

class TraderSuite extends AkkaSuite(ConfigFactory.parseString("""
	exchange{
	  trader {
	  	initCash = 500
	  	initGold = 10
	  }
	  richTrader {
	  	initGold = 100
	  }
	  poorTrader {
	  	initCash = 15
	  }
	}    
		""")) {
  "Trader" should "subscribe to an exchange once created" in {
    val trader = TestActorRef(Props(new Trader(testActor)))
    expectMsg(Subscribe(trader))
  }

  "Trader" should "un subscribe from an exchange on stop" in {
    val trader = TestActorRef(Props(new Trader(testActor)))
    //trader ! Kill
    //trader ! PoisonPill
    system.stop(trader)

    expectMsg(Subscribe(trader))
    expectMsg(Unsubscribe(trader))
  }

  "Trader" should "use file configuration" in {
    val exchange = TestProbe()
    val trader = TestActorRef[Trader](Props(new Trader(exchange.ref)))
    trader.underlyingActor.book.tell(GetStatus, testActor)
    expectMsg((500, Map("gold" -> 10)))
  }

  "PoorTrader" should "be poor" in {
    val exchange = TestProbe()
    val trader = TestActorRef[Trader](Props(new Trader(exchange.ref, "poorTrader")))
    trader.underlyingActor.book.tell(GetStatus, testActor)
    expectMsg((15, Map("gold" -> 10)))
  }
}