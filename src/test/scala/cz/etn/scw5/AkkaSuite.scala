package cz.etn.scw5

import akka.testkit.TestKit
import akka.actor.ActorSystem
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FlatSpec
import org.scalatest.FlatSpecLike
import org.scalatest.Matchers

class AkkaSuite extends TestKit(ActorSystem("exchange"))
  with FlatSpecLike
  with Matchers
  with ShutdownAfterAll