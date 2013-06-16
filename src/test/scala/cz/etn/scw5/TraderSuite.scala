package cz.etn.scw5

import akka.testkit.TestActorRef
import akka.actor.Props

class TraderSuite extends AkkaSuite {
  "Trader" should "subscribe to an exchange once created" in {
    val trader = TestActorRef(Props(new Trader(testActor)))
    expectMsg(Subscribe(trader))
  }
}