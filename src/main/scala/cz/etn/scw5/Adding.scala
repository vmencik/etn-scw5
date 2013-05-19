package cz.etn.scw5

import akka.actor.ActorSystem
import akka.actor.Actor
import akka.actor.Props

object Adding extends App {

  class AddingActor(add: Int, delay: Int) extends Actor {
    println("Instantiating")

    def receive = {
      case 3 => throw new RuntimeException("Error 3")
      case n: Int =>
        Thread.sleep(delay * 1000)
        println(Thread.currentThread.getName)
        val res = n + add
        println(res)
        println(sender)
        sender ! res
      case _ => println("received unknown message")
    }

    override def postRestart(e: Throwable) = {
      println(e.getMessage)
    }

  }

  val system = ActorSystem("workshop")
  val adder = system.actorOf(Props(new AddingActor(10, 1)), "first")
  val secondAdder = system.actorOf(Props(new AddingActor(999, 2)), "second")
  println(adder)
  println(secondAdder)

  println(Thread.currentThread.getName)
  adder ! 3
  adder ! 5
  println("sent messages")
  //secondAdder ! 8888

  Thread.sleep(2500)
  system.shutdown()

}