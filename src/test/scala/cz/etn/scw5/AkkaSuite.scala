package cz.etn.scw5

import akka.testkit.TestKit
import akka.actor.ActorSystem
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FlatSpec

class AkkaSuite extends TestKit(ActorSystem("exchange"))
  with FlatSpec
  with ShouldMatchers
  with ShutdownAfterAll