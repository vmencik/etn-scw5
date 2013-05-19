package cz.etn.scw5

import akka.testkit.TestKit
import org.scalatest.FlatSpec
import akka.actor.ActorSystem
import akka.testkit.TestActorRef
import org.scalatest.matchers.ShouldMatchers
import scala.collection.immutable.Queue
import org.scalatest.BeforeAndAfterAll

class ExchangeSuite extends TestKit(ActorSystem("exchange")) 
with FlatSpec 
with ShouldMatchers
with BeforeAndAfterAll {
  
  
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
  
  override protected def afterAll() = {
    system.shutdown()
    super.afterAll()
  }
}