package cz.etn.scw5

import akka.actor.Actor

class InsufficientFundsException extends RuntimeException {

}

class Book(initCash: Int, initCommodities: Map[String, Int] = Map.empty) extends Actor {

  private var _cash = initCash
  private var _commodities = initCommodities.withDefaultValue(0)

  def receive = {
    case Buy(commodity, quantity, price) =>
      val diff = quantity * price
      if (_cash < diff) throw new InsufficientFundsException
      _cash -= diff
      _commodities = _commodities updated (commodity, _commodities(commodity) + quantity)
    case Sell(commodity, quantity, price) =>
      _cash += quantity * price
      _commodities = _commodities updated (commodity, _commodities(commodity) - quantity)
  }

  def cash: Int = _cash

  def commodities: Map[String, Int] = _commodities

  override def preRestart(reason: Throwable, message: Option[Any]) {
    println("prerestart")
    super.preRestart(reason, message)
  }

  override def postRestart(reason: Throwable) {
    println("postrestart")
    super.postRestart(reason)
  }

  override def preStart = println("prestart")

  override def postStop = println("poststop")

}