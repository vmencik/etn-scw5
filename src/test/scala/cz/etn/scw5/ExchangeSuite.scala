package cz.etn.scw5

import akka.testkit.TestKit
import org.scalatest.FlatSpec
import akka.actor.ActorSystem
import akka.testkit.TestActorRef
import org.scalatest.matchers.ShouldMatchers
import scala.collection.immutable.Queue
import org.scalatest.BeforeAndAfterAll
import akka.testkit.TestActor

class ExchangeSuite extends AkkaSuite {

  "exchange" should "enqueue a quote" in {
    val q = Buy("gold", 10, 10)

    val actor = TestActorRef[Exchange]

    actor ! q

    println(Thread.currentThread.getName)

    actor.underlyingActor.commodityQueues should be(Map("gold" -> Queue(q)))

  }

  it should "enqueue two quotes" in {
    val one = Buy("gold", 10, 10)
    val two = Buy("gold", 12, 10)

    val actor = TestActorRef[Exchange]

    actor ! one
    actor ! two

    println(Thread.currentThread.getName)

    actor.underlyingActor.commodityQueues should be(Map("gold" -> Queue(one, two)))
  }

  it should "enqueue two commodities" in {
    val gold = Buy("gold", 10, 10)
    val silver = Buy("silver", 12, 10)

    val actor = TestActorRef[Exchange]

    actor ! gold
    actor ! silver

    println(Thread.currentThread.getName)

    actor.underlyingActor.commodityQueues should be(Map("gold" -> Queue(gold), "silver" -> Queue(silver)))
  }

  it should "pair matching buys and sells" in {
    val buy = Buy("gold", quantity = 10, price = 5)
    val sell = Sell("gold", quantity = 10, price = 4)

    val actor = TestActorRef[Exchange]

    actor ! buy
    actor ! sell

    actor.underlyingActor.commodityQueues should be(Map("gold" -> Queue()))
    actor.underlyingActor.tradeHistory should be(Seq(Trade("gold", 10, 5)))
  }

  it should "have no subcribers when created" in {
    val exchange = TestActorRef[Exchange]

    exchange.underlyingActor.subcribers should be('empty)

  }

  it should "subcribe trader" in {
    val exchange = TestActorRef[Exchange]

    exchange ! Subscribe(testActor)

    exchange.underlyingActor.subcribers should contain(testActor)

  }

  it should "ignore duplicate subscriber" in {
    val exchange = TestActorRef[Exchange]

    exchange ! Subscribe(testActor)
    exchange ! Subscribe(testActor)

    exchange.underlyingActor.subcribers should have size (1)
    exchange.underlyingActor.subcribers should contain(testActor)

  }

}