package code {

  import org.scalatest._
  import MyPrimitiveTypeMode._
  import Library._

  class AuthorSpec extends FlatSpec with DatabaseInit
      with Matchers with OneInstancePerTest with BeforeAndAfterAll with BeforeAndAfterEach {

    "foo" should "bar" in {

    }

    "Library.authors.insert" should "insert a new Author" in {
      
      inTransaction {
        authors.insert(new Author(11, "Sergiu", "Starciuc", Some("ciukstar@yahoo.com")))

        val a = from(authors)(as => where(as.id === 11) select(as)).singleOption.get

        a.id should be (11)
        a.firstName should be ("Sergiu")
        a.lastName should be ("Starciuc")
      }
       
    }

    override def beforeAll(): Unit = {
      configureDb()
      transaction { Library.create }
    }
    override def afterEach(): Unit = ()
    override def afterAll(): Unit = {
      closeDbConnection()
    }
  }
}
