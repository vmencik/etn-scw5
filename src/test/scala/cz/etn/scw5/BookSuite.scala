package cz.etn.scw5

import akka.testkit.TestActorRef
import akka.actor.Props
import akka.testkit.ImplicitSender

class BookSuite extends AkkaSuite with ImplicitSender {

  "Book" should "record buy of gold" in {

    val book = TestActorRef[Book](Props(new Book(initCash = 1500)))

    book ! Buy("gold", quantity = 10, price = 50)

    book.underlyingActor.cash should be(1000)
    book.underlyingActor.commodities should be(Map("gold" -> 10))

  }

  it should "record sell of gold" in {

    val book = TestActorRef[Book](Props(new Book(initCash = 1500, initCommodities = Map("gold" -> 20))))

    book ! Sell("gold", quantity = 10, price = 50)

    book.underlyingActor.cash should be(2000)
    book.underlyingActor.commodities should be(Map("gold" -> 10))
  }

  it should "produce exception when cash is insufficient" in {

    val book = TestActorRef[Book](Props(new Book(initCash = 100)))
    evaluating {
      book.underlyingActor.receive(Buy("gold", quantity = 10, price = 50))
    } should produce[InsufficientFundsException]
  }

  it should "return status on call" in {
    val book = TestActorRef[Book](Props(new Book(initCash = 120, initCommodities = Map("gold" -> 12))))
    book ! GetStatus
    expectMsg((120, Map("gold" -> 12)))
  }
}