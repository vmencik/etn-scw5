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
    
    actor.underlyingActor.commodityQueues should be(Map("gold" -> Queue(q)))
    
  }
  
  override protected def afterAll() = {
    system.shutdown()
    super.afterAll()
  }
}