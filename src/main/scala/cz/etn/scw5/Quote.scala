package cz.etn.scw5

sealed trait Quote {

  def commodity: String
  def quantity: Int
  def price: Int
  // def isComplement(other: Quote): Boolean = ???

}

case class Buy(commodity: String, quantity: Int, price: Int) extends Quote

case class Sell(commodity: String, quantity: Int, price: Int) extends Quote