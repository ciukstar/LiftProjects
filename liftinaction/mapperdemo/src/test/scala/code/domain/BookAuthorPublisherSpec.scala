package code.domain {

  import net.liftweb.mapper.{ DB, MapperRules, Schemifier }
  import net.liftweb.util.{ DefaultConnectionIdentifier, Helpers }
  import org.scalatest._

  class BookAuthorPublisherSpec extends FlatSpec
    with Matchers with OneInstancePerTest with BeforeAndAfterAll with BeforeAndAfterEach {

    "foo" should "bar" in {
      val pub1 = Publisher.create.name("Publisher1").description("The Publisher1").saveMe
      val pub2 = Publisher.create.name("Publisher2").description("The Publisher2").saveMe

      val auth1 = Author.create.title(Titles.Mr)
        .firstName("Author1 First Name").lastName("Author1 Last Name")
        .email("auth1@yahoo.com").saveMe

      val auth2 = Author.create.title(Titles.Miss)
        .firstName("Author2 First Name").lastName("Author2 Last Name")
        .email("auth2@yahoo.com").saveMe

      val b1 = Book.create.title("Title1").blurb("Blurb1")
      val b2 = Book.create.title("Title2").blurb("Blurb2")
      val b3 = Book.create.title("Title3").blurb("Blurb3")

      (auth1.books += b1).save
      (auth1.books += b2).save
      (auth2.books += b2).save
      (auth2.books += b3).save

      (pub1.books += (b1, b2)).save
      (pub2.books += b3).save

      Book.findAll should contain only (b1, b2, b3)
      (Author.findByKey(auth1.id.get) openOrThrowException "Not found").books should contain only (b1, b2)
      (Author.findByKey(auth2.id.get) openOrThrowException "Not found").books should contain only (b2, b3)
    }

    override def beforeAll: Unit = {
      DB.defineConnectionManager(DefaultConnectionIdentifier, DbVendor)
      MapperRules.columnName = (_, name) => Helpers.snakify(name)
      MapperRules.tableName = (_, name) => Helpers.snakify(name)
      Schemifier.schemify(true, Schemifier.infoF _, Book, Author, BookAuthors, Publisher)
    }
    override def afterEach: Unit = {
      Book.bulkDelete_!!()
      Author.bulkDelete_!!()
      Publisher.bulkDelete_!!()
    }
    override def afterAll: Unit = {
      DbVendor.tearDown
    }
  }

}
