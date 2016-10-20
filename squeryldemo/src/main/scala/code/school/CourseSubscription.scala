package code.school {

  import code.MyPrimitiveTypeMode._
  import org.squeryl.KeyedEntity
  import org.squeryl.dsl.CompositeKey2

  class CourseSubscription(val courseId: Long, val studentId: Long, val grade: Float) extends KeyedEntity[CompositeKey2[Long, Long]] {
    override def id = compositeKey(courseId, studentId)
  }
}
