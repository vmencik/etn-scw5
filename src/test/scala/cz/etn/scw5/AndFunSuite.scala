package cz.etn.scw5

import org.scalatest.FlatSpec
import org.scalatest.Matchers

class AndFunSuite extends FlatSpec
  with Matchers {

  import Implicits._

  "AndFun" should "composes functions" in {
    val x: (Int => Boolean) = _ > 5
    val y = (i: Int) => i < 10

    val z = x && y
    z(3) should be(false)
    z(8) should be(true)
    z(12) should be(false)

  }

}