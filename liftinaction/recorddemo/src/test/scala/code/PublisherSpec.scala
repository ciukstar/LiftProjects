package code {

  import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, FlatSpec, OneInstancePerTest}
  import org.scalatest.Matchers
  import Bookstore._

  class PublisherSpec extends FlatSpec with Matchers with OneInstancePerTest with BeforeAndAfterAll with BeforeAndAfterEach {

    "foo" should "bar" in {
      publishers.insert(Seq(
        Publisher.createRecord.name("Manning"),
        Publisher.createRecord.name("Penguin"),
        Publisher.createRecord.name("Bloomsbury")
      ))

    }

    override def beforeAll(): Unit = ()
    override def beforeEach(): Unit = ()
    override def afterAll(): Unit = ()
  }
}
