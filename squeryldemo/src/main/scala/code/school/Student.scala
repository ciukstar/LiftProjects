package code.school {

  import org.squeryl.KeyedEntity
  import org.squeryl.dsl.ManyToMany

  class Student(val id: Long, val firstName: String, val lasteName: String) extends KeyedEntity[Long] {
    def this(firstName: String, lastName: String) = this(-1, firstName, lastName)

    lazy val courses: ManyToMany[Course, CourseSubscription] =
      School.courseSubscriptions.right(this)
  }
}
