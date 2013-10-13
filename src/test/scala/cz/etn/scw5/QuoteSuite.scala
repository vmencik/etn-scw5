package cz.etn.scw5

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.BeforeAndAfterAll
import org.scalatest.FlatSpec

class QuoteSuite extends FlatSpec
  with ShouldMatchers {

  val buy = Buy("gold", quantity = 10, price = 5)
  val sell = Sell("gold", quantity = 10, price = 4)

  "buy quote" should "opposite to sell quote" in {
    buy.isOppositeOf(sell) should be(true)
  }

  "sell quote" should "opposite to buy quote" in {
    sell.isOppositeOf(buy) should be(true)
  }

  "buy quote" should "match matching sell quote" in {
    buy.matches(sell) should be(true)
  }

  "sell quote" should "match matching buy quote" in {
    sell.matches(buy) should be(true)
  }

  "buy quote" should "not match mismatching sell quote" in {
    val buy = Buy("gold", quantity = 10, price = 3)
    val sell = Sell("gold", quantity = 10, price = 4)
    buy.matches(sell) should be(false)
  }

}