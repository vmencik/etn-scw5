package cz.etn.scw5

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.Props

class Trader(exchange: ActorRef) extends Actor {

  private var book: ActorRef = _

  def receive = {
    case t: Trade =>
      println(self.path.name + ": " + t)
    case b: Buy =>
      book.forward(b)
  }

  // Po inicializaci konstruktorem. Volano i po restartu.
  override def preStart() {
    book = context.actorOf(Props(new Book(1000, Map("gold" -> 10))))
    exchange ! Subscribe(self)
  }

  override def postStop() {
    exchange ! Unsubscribe(self)
  }

}