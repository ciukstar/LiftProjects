package code {

  import java.awt.event.{ ActionEvent }
  import javax.swing.JButton
  import org.scalatest._
  import Preamble._


  class ActionListenerSpec extends FlatSpec with Matchers with OneInstancePerTest {

    "foo" should "bar" in {

      val button = new JButton

      button.addActionListener( (_: ActionEvent) => println("pressed!") )

      1 should be (1)
    }

  }
}
