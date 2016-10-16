package code.domain {

  import java.text.{DateFormat => Df}
  import net.liftweb.db.DB
  import net.liftweb.mapper.{ By, MapperRules, NotBy, Schemifier }
  import net.liftweb.util.{DefaultConnectionIdentifier, Helpers}
  import org.scalatest._

  class PublisherSpec extends FlatSpec with Matchers
    with OneInstancePerTest with BeforeAndAfterAll with BeforeAndAfterEach {

    "Publisher.map(_.name.get).openOr(\"unknown\")" should "extract publisher name" in {

      val p1 = Publisher.create.name("P1").description("D1").saveMe
      val b11 = Book.create.title("T11").blurb("B11").publishedOn(Df.getDateInstance.parse("January 1, 2000"))
      val b12 = Book.create.title("T12").blurb("B12").publishedOn(Df.getDateInstance.parse("January 2, 2000"))

      val p2 = Publisher.create.name("P2").description("D2").saveMe
      val b21 = Book.create.title("T21").blurb("B21").publishedOn(Df.getDateInstance.parse("January 3, 2000"))
      val b22 = Book.create.title("T22").blurb("B22").publishedOn(Df.getDateInstance.parse("January 4, 2000"))

      (p1.books += (b11, b12)).save
      (p2.books += (b21, b22)).save

      val p = Publisher.find(By(Publisher.name, p2.name.get))

      val name = p.map(_.name.get).openOr("unknown")

      name should be (p2.name.get)

    }

    "Publisher.find(NotBy(Publisher.name))" should "return one of Publishers that has not the given name" in {

      val p1 = Publisher.create.name("Publisher1").saveMe
      val p2 = Publisher.create.name("Publisher2").saveMe
      val p3 = Publisher.create.name("Publisher3").saveMe

      val res = Publisher.find(NotBy(Publisher.name, p1.name.get))

      val p = res openOrThrowException "fail"

      p should not be p1
      Some(p) should contain oneOf (p2,p3)
    }

    "Publisher.find(NotBy(Publisher.name))" should "find and fetch a Publisher which name is not the given one" in {

      val p1 = Publisher.create.name("Publisher1").saveMe
      val p2 = Publisher.create.name("Publisher2").saveMe

      val p = Publisher.find(NotBy(Publisher.name, "Publisher1")) openOrThrowException "Not found"

      p should not be p1
      p should be (p2)
    }

    "Publisher.find(By(Publishher.name))" should "find and fetch a Publisher by its name" in {

      val p1 = Publisher.create.name("Publisher1").saveMe
      val p2 = Publisher.create.name("Publisher2").saveMe

      val p = Publisher.find(By(Publisher.name, "Publisher1")) openOrThrowException "Not Found"

      p should be (p1)
    }

    "Publisher.findAll" should "fetch all Publishers" in {

      val p1 = Publisher.create.name("Publisher1").saveMe
      val p2 = Publisher.create.name("Publisher2").saveMe

      Publisher.findAll should contain only (p2,p1)
    }

    "Publisher.find(id)" should "find and fetch a Publisher by its id" in {

      val p1 = Publisher.create.name("Publisher1").saveMe
      val p2 = Publisher.create.name("Publisher2").saveMe

      (Publisher.find(p1.id.get) openOrThrowException "Not found") should be (p1)
    }

    override def beforeAll(): Unit = {
      DB.defineConnectionManager(DefaultConnectionIdentifier, DbVendor)
      MapperRules.columnName = (_, name) => Helpers.snakify(name)
      MapperRules.tableName = (_, name) => Helpers.snakify(name)
      Schemifier.schemify(true, Schemifier.infoF _, Publisher, Book)
    }
    override def afterEach(): Unit = {
      Publisher.bulkDelete_!!()
    }
    override def afterAll(): Unit = {
      DbVendor.tearDown
    }
  }

}
