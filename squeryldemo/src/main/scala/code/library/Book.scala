package code.library {

  class Book(
    val id: Long,
    var title: String,
    var authorId: Long,
    var coAuthorId: Option[Long]
  ) {
    def this() = this(0, "", 0, Some(0))
  }
}
