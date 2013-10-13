package cz.etn.scw5

import akka.testkit.TestKit
import akka.actor.ActorSystem
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FlatSpec
import org.scalatest.FlatSpecLike
import org.scalatest.Matchers
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory

class AkkaSuite(config: Config = ConfigFactory.load) extends TestKit(ActorSystem("exchange", config))
  with FlatSpecLike
  with Matchers
  with ShutdownAfterAll