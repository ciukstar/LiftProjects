package code.school {

  import code.DatabaseInit
  import org.scalatest._
  import code.MyPrimitiveTypeMode._
  import School._

  class CourseSubscriptionSpec extends FlatSpec with DatabaseInit
    with Matchers with OneInstancePerTest with BeforeAndAfterAll with BeforeAndAfterEach {

    "foo" should "bar" in {

      inTransaction {
        val subj = new Subject("Subject")
        subjects.insert(subj)

        val c1 = new Course("Course1", subj)
        val c2 = new Course("Course2", subj)

        val s1 = new Student("FN1", "LN1")
        val s2 = new Student("FN2", "LN2")

        courses.insert(Seq(c1, c2))

      }
      fail("To Be Continued")

    }

    override def beforeAll(): Unit = {
      configureDb()
      inTransaction { School.create }
    }
    override def beforeEach(): Unit = {
      inTransaction {
        courses.deleteWhere(c => 1 === 1)
        students.deleteWhere(s => 1 === 1)
      }
    }
    override def afterAll(): Unit =
      closeDbConnection()
  }
}
