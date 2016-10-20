package code.school {

  import org.squeryl.KeyedEntity
  import org.squeryl.dsl.OneToMany

  class Subject(val id: Long, val name: String) extends KeyedEntity[Long] {
    def this(name: String) = this(-1, name)
    def this() = this(-1, "")
    lazy val courses: OneToMany[Course] = School.subjectToCourses.left(this)
  }
}
