package cz.etn.scw5

import akka.actor.Actor
import scala.collection.immutable.Queue
import akka.actor.ActorRef
import akka.actor.ActorLogging

class Exchange extends Actor with ActorLogging {

  private var cq = Map[String, Queue[(Quote, ActorRef)]]().withDefaultValue(Queue())
  private var th = List[Trade]()
  private var traders = Set[ActorRef]()

  override def preStart(): Unit = {
    log.info("Starting Exchange")
  }

  def receive = {
    case q: Quote =>
      log.info("Received Quote: " + q)
      val queue = cq(q.commodity)

      val (prefix, suffix) = queue.span(!_._1.matches(q))

      val (newQueue, newHistory) = if (suffix.isEmpty) {
        // sender -> odesilatel posledni zpravy
        (prefix.enqueue(q -> sender), th)
      } else {
        val attrs = (q.commodity, q.quantity, math.max(q.price, suffix.head._1.price))
        val trade = (Trade.apply _).tupled(attrs)
        val buy = (Buy.apply _).tupled(attrs)
        val sell = (Sell.apply _).tupled(attrs)
        val (buyer, seller) = q match {
          case _: Buy => (sender, suffix.head._2)
          case _: Sell => (suffix.head._2, sender)
        }

        log.info("Matched trade: " + trade)
        buyer ! buy
        seller ! sell
        traders.foreach(_ ! trade)
        (prefix ++ suffix.tail, th :+ trade)
      }

      cq = cq.updated(q.commodity, newQueue)
      th = newHistory

    case Subscribe(trader) =>
      traders = traders + trader
      log.info("Subscribed " + trader)

    case Unsubscribe(trader) =>
      traders = traders - trader
      log.info("Unsubscribed " + trader)
  }

  def commodityQueues = cq

  def tradeHistory = th

  def subcribers = traders
}