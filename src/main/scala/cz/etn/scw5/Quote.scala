package cz.etn.scw5

sealed trait Quote {

  def commodity: String
  def quantity: Int
  def price: Int
  def isOppositeOf(other: Quote): Boolean = (this, other) match {
    case (_: Sell, _: Buy) | (_: Buy, _: Sell) => true
    case _ => false
  }

  //  def matches: Quote => Boolean = //isOppositeOf && matchesCommodity && matchesQuantity && matchesPrice

  def matches(other: Quote): Boolean =
    isOppositeOf(other) && commodity == other.commodity && quantity == other.quantity && matchesPrice(other)

  //  protected def matchesCommodity(other: Quote): Boolean
  //  protected def matchesQuantity(other: Quote): Boolean
  protected def matchesPrice(other: Quote): Boolean

}

case class Buy(commodity: String, quantity: Int, price: Int) extends Quote {
  protected def matchesPrice(other: Quote): Boolean = price >= other.price
}

case class Sell(commodity: String, quantity: Int, price: Int) extends Quote {
  protected def matchesPrice(other: Quote): Boolean = price <= other.price
}

case class Trade(commodity: String, quantity: Int, price: Int)