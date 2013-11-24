package cz.etn.scw5

import javax.swing.JFrame
import javax.swing.JTextField
import java.awt.BorderLayout
import akka.actor.ActorRef
import javax.swing.JButton
import java.awt.event.ActionListener
import java.awt.event.ActionEvent

class TraderInput(exchange: ActorRef, trader: ActorRef) extends JFrame(trader.path.name) {

  override protected def frameInit() {
    super.frameInit
    val inputField = new JTextField()
    val button = new JButton("Odeslat")

    // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    getContentPane().add(inputField, BorderLayout.CENTER)
    getContentPane().add(button, BorderLayout.SOUTH)
    button.addActionListener(new ActionListener() {
      override def actionPerformed(event: ActionEvent): Unit = {
        val text = inputField.getText()
        println("Text input: " + text)
        exchange.tell(ParseQuote(text), trader)
        inputField.setText("")
      }
    })
    setSize(500, 500)
    setVisible(true)
  }
}