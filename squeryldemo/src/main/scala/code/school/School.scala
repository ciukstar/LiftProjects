package code.school {

  import code.MyPrimitiveTypeMode
  import org.squeryl.Schema
  import MyPrimitiveTypeMode._

  object School extends Schema()(MyPrimitiveTypeMode) {
    val courses = table[Course]
    val subjects = table[Subject]
    val students = table[Student]

    val subjectToCourses =
      oneToManyRelation(subjects, courses).via((s, c) => s.id === c.subjectId)

    val courseSubscriptions =
      manyToManyRelation(courses, students).
        via[CourseSubscription]((c, s, cs) => (cs.courseId === c.id, cs.studentId === s.id))
  }
}
