package cz.etn.scw5

import org.scalatest.Suites
import org.scalatest.DoNotDiscover

@DoNotDiscover
class TestSuite extends Suites(
  new ExchangeSuite,
  new QuoteSuite,
  new TraderSuite,
  new BookSuite,
  new AndFunSuite,
  new QuoteParserSuite)