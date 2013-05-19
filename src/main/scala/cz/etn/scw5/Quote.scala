package cz.etn.scw5

sealed trait Quote {

  def commodity: String
  def quantity: Int
  def price: Int
  def isOppositeOf(other: Quote): Boolean = (this, other) match {
    case (_:Sell, _:Buy) | (_:Buy, _:Sell) => true
    case _ => false
  }

}

case class Buy(commodity: String, quantity: Int, price: Int) extends Quote

case class Sell(commodity: String, quantity: Int, price: Int) extends Quote

case class Trade(commodity: String, quantity: Int, price: Int)