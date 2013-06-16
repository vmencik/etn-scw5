package cz.etn.scw5

import akka.testkit.TestActorRef
import akka.actor.Props

class BookSuite extends AkkaSuite {

  "Book" should "record buy of gold" in {

    val book = TestActorRef[Book](Props(new Book(initCash = 1500)))

    book ! Buy("gold", quantity = 10, price = 50)

    book.underlyingActor.cash should be(1000)
    book.underlyingActor.commodities should be(Map("gold" -> 10))

  }

}