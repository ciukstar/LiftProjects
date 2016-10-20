package code.school {

  import code.DatabaseInit
  import org.scalatest._
  import code.MyPrimitiveTypeMode._
  import School._

  class SchoolSpec extends FlatSpec with Matchers
    with DatabaseInit with OneInstancePerTest with BeforeAndAfterAll with BeforeAndAfterEach {

    "OneToMany.associate(c: Course)" should "should associate and persist c" in {

      inTransaction {
        val s = new Subject("Subject1");
        subjects.insert(s)
        val c1 = new Course("Course1", s)
        val c2 = new Course("Course2", s)

        s.courses.associate(c1)
        s.courses.associate(c2)

        val res = from(School.subjects)(s => where(1 === 1) select (s)).single

        res.courses.seq should have('size(2))
      }
    }

    "Persisting a new Subject" should "generate a new id" in {

      val s = new Subject("Subject1");

      s.id should be(-1)

      inTransaction {
        subjects.insert(s)
      }

      s.id should not be (-1)
    }

    override def beforeAll(): Unit = {
      configureDb()
      inTransaction { School.create }
    }
    override def beforeEach(): Unit = {
      inTransaction {
        courses.deleteWhere(c => 1 === 1)
        subjects.deleteWhere(s => 1 === 1)
      }
    }
    override def afterAll(): Unit =
      closeDbConnection()
  }
}
