package cz.etn.scw5

import akka.actor.ActorSystem
import akka.actor.Actor
import akka.actor.Props

object Adding extends App {

  class AddingActor(add: Int, delay: Int) extends Actor {

    def receive = {
      case n: Int =>
        Thread.sleep(delay * 1000)
        println(Thread.currentThread.getName)
        val res = n + add
        println(res)
        println(sender)
        sender ! res
      case _ => println("received unknown message")
    }

  }

  val system = ActorSystem("workshop")
  val adder = system.actorOf(Props(new AddingActor(10, 1)))
  val secondAdder = system.actorOf(Props(new AddingActor(999, 2)))

  println(Thread.currentThread.getName)
  adder ! 255
  secondAdder ! 8888

  Thread.sleep(2500)
  system.shutdown()

}