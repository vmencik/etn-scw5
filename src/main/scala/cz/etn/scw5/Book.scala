package cz.etn.scw5

import akka.actor.Actor

class InsufficientFundsException extends RuntimeException {

}

case object GetStatus

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
    case GetStatus => sender ! (_cash, _commodities)
  }

  def cash: Int = _cash

  def commodities: Map[String, Int] = _commodities

}
