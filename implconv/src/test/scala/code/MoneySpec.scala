package code {

  import org.scalatest._


  class MoneySpec extends FlatSpec with Matchers with OneInstancePerTest {

    "foo" should "bar" in {

      ( Euro(3) + Dollar(2) ) should be ( Euro(4) )
    }

    "Two dollars plus tree euros" should "be four euros" in {

      ( Dollar(2) + Euro(3)) should be (Dollar(8))
    }

    "Adding two euro to one euro" should "be three euros" in {
      ( Euro(1) + Euro(2) ) should be (Euro(3))
    }

    "Adding one dollars to one dollar" should "be three dollars" in {
      ( Dollar(1) + Dollar(2) ) should be (Dollar(3))
    }
  }
}
