package code.domain {

  import net.liftweb.db.DB
  import net.liftweb.mapper.{MapperRules, NotBy, Schemifier}
  import net.liftweb.util.{DefaultConnectionIdentifier, Helpers}
  import org.scalatest._

  class AuthorSpec extends FlatSpec with Matchers
    with OneInstancePerTest with BeforeAndAfterAll with BeforeAndAfterEach {

    "Author.find(NotBy(MapperField))" should "find an any Author that has a diffrent email" in {
      val a1 = Author.create.title(Titles.Mr).firstName("FN1").lastName("LN1").email("e1@comp.com").saveMe
      val a2 = Author.create.title(Titles.Mr).firstName("FN2").lastName("LN2").email("e2@comp.com").saveMe
      val a3 = Author.create.title(Titles.Mr).firstName("FN3").lastName("LN3").email("e3@comp.com").saveMe

      val res = Author.find(NotBy(Author.email, a1.email.get)) openOrThrowException "fail"

      res should not be a1
      Some(res) should contain oneOf (a2, a3)
    }

    override def beforeAll(): Unit = {
      DB.defineConnectionManager(DefaultConnectionIdentifier, DbVendor)
      MapperRules.columnName = (_, name) => Helpers.snakify(name)
      MapperRules.tableName = (_, name) => Helpers.snakify(name)
      Schemifier.schemify(true, Schemifier.infoF _, Author)
    }
    override def afterEach(): Unit = {
      Author.bulkDelete_!!()
    }
    override def afterAll(): Unit = {
      DbVendor.tearDown
    }
  }
}
