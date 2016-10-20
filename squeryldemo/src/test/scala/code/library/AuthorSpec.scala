package code.library {

  import org.scalatest._
  import code._
  import MyPrimitiveTypeMode._
  import Library._

  class AuthorSpec extends FlatSpec with DatabaseInit
    with Matchers with OneInstancePerTest with BeforeAndAfterAll with BeforeAndAfterEach {

    "update(authors)" should "partially update an Author" in {
      val incorrect = "Stanciuc"
      val correct = "Starciuc"
      val sergiu = new Author(-1, "Sergiu", incorrect, Some("ciukstar@yahoo.com"))

      inTransaction {
        authors.insert(sergiu)
      }

      inTransaction {
        update(authors)(a => where(a.lastName === incorrect) set(a.lastName := correct))
      }

      inTransaction {
        from(authors)(a => where(a.lastName === incorrect) select(a)).headOption should be (None)
        from(authors)(a => where(a.lastName === correct) select(a)).headOption should not be None
      }
    }

    "Library.authors.update(a: Author)" should "should fully update an Author" in {
      val correct = "Starciuc"
      val incorrect = "Stanciuc"
      val sergiu = new Author(11, "Sergiu", incorrect, Some("ciukstar@yahoo.com"))
      inTransaction {
        authors.insert(sergiu)
      }

      inTransaction {
        val a = from(authors)(a => where(a.lastName === incorrect) select(a)).head
        a.lastName = correct
        authors.update(a)
      }

      inTransaction {
        val res =  from(authors)(a => where(a.lastName === correct) select(a)).head
        res should have ('id (sergiu.id))
        res should have ('firstName (sergiu.firstName))
        res should have ('lastName (correct))
      }
    }

    "An IllegalStateException" should "be thrown by a query executed outside of a transaction context" in {

      val sergiu = new Author(13, "Sergiu", "Sergiu", Some("ciukstar@yahoo.com"))
      val zarina = new Author(14, "Zarina", "Musina", Some("zamusina@mail.ru"))

      inTransaction {
        authors.insert(sergiu)
        authors.insert(zarina)
      }

      an [IllegalStateException] should be thrownBy from(authors)(a => where(a.lastName === "Musina") select (a)).head
    }

    "An IllegalStateException" should "be thrown by an insert executed aoutside of a transaction" in {

      val sergiu = new Author(11, "Sergiu", "Starciuc", Some("ciukstar@yahoo.com"))

      an [IllegalStateException] should be thrownBy authors.insert(sergiu)
    }

    "Library.authors.insert" should "insert a new Author" in {

      val sergiu = new Author(13, "Sergiu", "Starciuc", Some("ciukstar@yahoo.com"))

      inTransaction {
        authors.insert(sergiu)

        val a = from(authors)(a => where(a.firstName === sergiu.firstName) select (a)).head

        a.firstName should be(sergiu.firstName)
        a.lastName should be(sergiu.lastName)
      }

    }

    override def beforeAll(): Unit = {
      configureDb()
      transaction { Library.create }
    }
    override def beforeEach(): Unit = {
      transaction {
        authors.deleteWhere(a => 1 === 1)
      }
    }
    override def afterAll(): Unit = {
      closeDbConnection()
    }
  }
}
