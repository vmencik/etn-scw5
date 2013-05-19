package cz.etn.scw5

import akka.actor.Actor
import scala.collection.immutable.Queue

class Exchange extends Actor {

  private var cq = Map[String, Queue[Quote]]().withDefaultValue(Queue())
  private var th = List[Trade]()

  def receive = {
    case q: Quote =>
      //Thread.sleep(1000)
      println(Thread.currentThread.getName)
      val queue = cq(q.commodity)

      val (prefix, suffix) = queue.span(!_.matches(q))

      val (newQueue, newHistory) = if (suffix.isEmpty) {
        (prefix.enqueue(q), th)
      } else {
        (prefix ++ suffix.tail, th :+ Trade(q.commodity, q.quantity, math.max(q.price, suffix.head.price)))
      }      

      cq = cq.updated(q.commodity, newQueue)
      th = newHistory
      
    // val queue = cq(q.commodity)

  }

  def commodityQueues = cq

  def tradeHistory = th
}