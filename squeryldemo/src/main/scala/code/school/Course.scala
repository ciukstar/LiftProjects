package code.school {

  import org.squeryl.KeyedEntity
  import org.squeryl.dsl.{ ManyToMany, ManyToOne }

  class Course(val id: Long, val name: String, val subjectId: Long) extends KeyedEntity[Long] {

    def this(name: String, subject: Subject) = this(-1, name, subject.id)
    def this() = this(-1, "", -1)

    lazy val subject: ManyToOne[Subject] = School.subjectToCourses.right(this)
    lazy val students: ManyToMany[Student, CourseSubscription] = School.courseSubscriptions.left(this)
  }
}
