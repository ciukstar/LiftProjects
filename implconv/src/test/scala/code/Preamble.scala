package code {

  import scala.language.implicitConversions
  import java.awt.event.{ActionEvent, ActionListener}

  object Preamble {
    implicit def function2ActionListener(f: ActionEvent => Unit) =
      new ActionListener {
        override def actionPerformed(e: ActionEvent) = f(e)
      }
  }
}
