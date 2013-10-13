package cz.etn.scw5

import akka.testkit.TestActorRef
import akka.actor.Props
import akka.actor.Kill
import akka.actor.PoisonPill

class TraderSuite extends AkkaSuite {
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
    val trader = TestActorRef(Props(new Trader(testActor)))
    trader.getSingleChild("")
  }
}