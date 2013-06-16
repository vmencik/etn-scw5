package cz.etn.scw5

import org.scalatest.Suites

class TestSuite extends Suites(
  new ExchangeSuite,
  new QuoteSuite,
  new TraderSuite,
  new BookSuite)