package cz.etn.scw5

import org.scalatest.FunSuite
import org.scalatest.Matchers

class QuoteParserSuite extends FunSuite with Matchers {
  test("buy Quote") {
    ParseQuote("buy gold 10 per 100") should be(Buy("gold", 10, 100))
  }

  test("sell Quote") {
    ParseQuote("sell silver 120 per 1200") should be(Sell("silver", 120, 1200))
  }

  test("invalid input") {
    evaluating {
      ParseQuote("invalid input")
    } should produce[IllegalArgumentException]
  }
}