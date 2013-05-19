package cz.etn.scw5

import akka.actor.Actor
import scala.collection.immutable.Queue

class Exchange extends Actor {
  
  private var cq = Map[String, Queue[Quote]]()
  
  def receive = {
    case q: Quote =>
      val queue = cq.getOrElse(q.commodity, Queue())
      cq = cq.updated(q.commodity, queue enqueue q)
  }

  def commodityQueues = cq 
  
}