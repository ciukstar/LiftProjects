package code {

  import java.sql.DriverManager
  import org.scalatest._
  import org.squeryl.adapters.H2Adapter
  import org.squeryl.{Session, SessionFactory}
  import MyPrimitiveTypeMode._
  import Library._

  class AuthorSpec extends FlatSpec
      with Matchers with OneInstancePerTest with BeforeAndAfterAll with BeforeAndAfterEach {

    "foo" should "bar" in {

    }

    "Library.authors.insert" should "insert a new Author" in {

      
      inTransaction {
        Library.create
        authors.insert(new Author(11, "Sergiu", "Starciuc", Some("ciukstar@yahoo.com")))

        val a = from(authors)(as => where(as.id === 11) select(as)).singleOption.get

        a.id should be (11)
        a.firstName should be ("Sergiu")
        a.lastName should be ("Starciuc")
      }
       
    }

    override def beforeAll(): Unit = {
      Class.forName("org.h2.Driver")
      val conn = DriverManager.getConnection("jdbc:h2:mem:mydb;DB_CLOSE_DELAY=-1")
      val f = SessionFactory.concreteFactory = Some(() => Session.create(conn, new H2Adapter))
      
    }
    override def afterEach(): Unit = ()
    override def afterAll(): Unit = ()
  }
}
