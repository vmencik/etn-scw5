package cz.etn.scw5

import akka.actor.ActorRef

case class Subscribe(subscriber: ActorRef)

case class Unsubscribe(subscriber: ActorRef)