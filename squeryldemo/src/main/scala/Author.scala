package code {

  class Author(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val email: Option[String]
  ) {
    def this() = this(0,"","",Some(""))
  }
}