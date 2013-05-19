package cz.etn.scw5

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers

class DummySuite extends FunSuite with ShouldMatchers {
  
  test("dummy") {
    1 + 1 should be (2)
  }
  
}