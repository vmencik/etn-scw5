package cz.etn.scw5

sealed trait Quote {

  import Implicits._

  def commodity: String
  def quantity: Int
  def price: Int
  def isOppositeOf(other: Quote): Boolean = (this, other) match {
    case (_: Sell, _: Buy) | (_: Buy, _: Sell) => true
    case _ => false
  }

  def matches: Quote => Boolean = isOppositeOf _ && matchesCommodity && matchesQuantity && matchesPrice

  protected def matchesCommodity(other: Quote): Boolean = commodity == other.commodity
  protected def matchesQuantity(other: Quote): Boolean = quantity == other.quantity
  protected def matchesPrice(other: Quote): Boolean

}

case class Buy(commodity: String, quantity: Int, price: Int) extends Quote {
  protected def matchesPrice(other: Quote): Boolean = price >= other.price
}

case class Sell(commodity: String, quantity: Int, price: Int) extends Quote {
  protected def matchesPrice(other: Quote): Boolean = price <= other.price
}

case class Trade(commodity: String, quantity: Int, price: Int)