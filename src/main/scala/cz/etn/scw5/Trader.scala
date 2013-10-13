package cz.etn.scw5

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.Props
import akka.event.Logging
import akka.actor.ActorLogging

class Trader(exchange: ActorRef) extends Actor with ActorLogging {

  private var book: ActorRef = _

  def receive = {
    case t: Trade =>
      log.info(self.path.name + " xxxx: " + t)
    case b: Buy =>
      book.forward(b)
  }

  // Po inicializaci konstruktorem. Volano i po restartu.
  override def preStart() {
    val traderConfig = context.system.settings.config.getConfig("exchange.trader")
    val initCash = traderConfig.getInt("initCash")
    val initGold = traderConfig.getInt("initGold")
    book = context.actorOf(Props(new Book(initCash, Map("gold" -> initGold))))
    exchange ! Subscribe(self)
  }

  override def postStop() {
    exchange ! Unsubscribe(self)
  }

}