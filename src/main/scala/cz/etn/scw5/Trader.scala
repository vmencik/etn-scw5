package cz.etn.scw5

import akka.actor.Actor
import akka.actor.ActorRef

class Trader(exchange: ActorRef) extends Actor {
  
  def receive = {
    case t: Trade =>
      println(self.path.name + ": " + t)
  }

}