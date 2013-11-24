package cz.etn.scw5

import javax.swing.JFrame
import javax.swing.JTextField
import java.awt.BorderLayout
import akka.actor.ActorRef
import javax.swing.JButton
import java.awt.event.ActionListener
import java.awt.event.ActionEvent
import scala.swing.Frame
import scala.swing.TextField
import scala.swing.Button
import scala.swing.GridPanel
import scala.swing.event.ButtonClicked
import java.awt.Dimension

class TraderInput(exchange: ActorRef, trader: ActorRef) extends Frame() {

  title = trader.path.name

  val inputField = new TextField()
  val button = new Button("Odeslat") {
    reactions += {
      case ButtonClicked(_) =>
        val text = inputField.text
        println("Text input: " + text)
        exchange.tell(ParseQuote(text), trader)
        inputField.text = ""
    }
  }

  contents = new GridPanel(2, 1) {
    contents ++= Seq(inputField, button)
  }
  size = new Dimension(500, 500)
  visible = true
}