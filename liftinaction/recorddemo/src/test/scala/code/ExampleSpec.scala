package code

import net.liftweb.common.Empty
import org.scalatest._

class ExampleSpec extends FlatSpec with Matchers {

  "Example" should "have a test!" in {

    val r = Example.createRecord.name("Tim").email(Empty)
    val errs = r.validate

    errs should not be empty
    errs.map(_.msg).head.text should be ("Must be more than 5 characters")

  }
}
