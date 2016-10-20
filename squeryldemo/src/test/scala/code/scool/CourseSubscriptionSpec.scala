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
        val c1 = courses.insert(new Course("Course1", subj))
        val c2 = courses.insert(new Course("Course2", subj))
        val s1 = students.insert(new Student("FN1", "LN1"))
        val s2 = students.insert(new Student("FN2", "LN2"))
        val sub11 = s1.courses.associate(c1)
        val sub12 = s1.courses.associate(c2)
        val sub21 = s2.courses.associate(c1)
        val sub22 = s2.courses.associate(c2)
      }

      inTransaction {
       
        val s1 = from(students)(s => where(s.firstName === "FN1") select(s)).singleOption
      }

      inTransaction {
        from(courses)(c => where(c.name === "Course1") select(c)).singleOption
      }
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
