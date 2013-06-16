package cz.etn.scw5

import akka.actor.Actor

class Book(initCash: Int) extends Actor {

  private var _cash = initCash
  private var _commodities = Map[String, Int]().withDefaultValue(0)

  def receive = {
    case Buy(commodity, quantity, price) =>
      _cash -= quantity * price
      _commodities = _commodities updated (commodity, _commodities(commodity) + quantity)
  }

  def cash: Int = _cash

  def commodities: Map[String, Int] = _commodities

}