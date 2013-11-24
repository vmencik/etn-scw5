package cz.etn.scw5

object ParseQuote {
  val quote = """(buy|sell) (\w+) (\d+) per (\d+)""".r

  def apply(text: String): Quote = text match {
    case quote("buy", commodity, quantity, price) =>
      Buy(commodity, quantity.toInt, price.toInt)
    case quote("sell", commodity, quantity, price) =>
      Sell(commodity, quantity.toInt, price.toInt)
    case _ => throw new IllegalArgumentException
  }
}