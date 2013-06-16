package cz.etn.scw5

import akka.testkit.TestKit
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.BeforeAndAfterAll
import org.scalatest.FlatSpec
import akka.actor.ActorSystem
import org.scalatest.Suite

trait ShutdownAfterAll extends BeforeAndAfterAll {
  this: TestKit with Suite =>

  override protected def afterAll() = {
    system.shutdown()
    super.afterAll()
  }

}