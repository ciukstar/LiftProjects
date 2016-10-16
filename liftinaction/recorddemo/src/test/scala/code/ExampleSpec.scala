package code

import net.liftweb.common.Empty
import org.scalatest._

class ExampleSpec extends FlatSpec with Matchers {

  "Example" should "have a test!" in {

    val r = Example.createRecord.name("Tim").email(Empty)
    r.validate should not be empty
  }
}
