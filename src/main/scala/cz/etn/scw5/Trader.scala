package cz.etn.scw5

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.Props
import akka.event.Logging
import akka.actor.ActorLogging

class Trader(exchange: ActorRef, traderName: String = "trader") extends Actor with ActorLogging {

  private var _book: ActorRef = _

  def receive = {
    case t: Trade =>
      log.info(self.path.name + ": received trade " + t)
    case q: Quote =>
      _book.forward(q)
      log.info(self.path.name + ": received quote " + q)
  }

  def book = _book

  // Po inicializaci konstruktorem. Volano i po restartu.
  override def preStart() {

    val traderConfig = context.system.settings.config.getConfig("exchange." + traderName).
      withFallback(context.system.settings.config.getConfig("exchange.trader"))
    val initCash = traderConfig.getInt("initCash")
    val initGold = traderConfig.getInt("initGold")

    log.info(self.path.name + " starting, subscribing to " + exchange)
    _book = context.actorOf(Props(new Book(initCash, Map("gold" -> initGold))))
    exchange ! Subscribe(self)
  }

  override def postStop() {
    exchange ! Unsubscribe(self)
    log.info(self.path.name + " unsubscribing from " + exchange)
  }

}