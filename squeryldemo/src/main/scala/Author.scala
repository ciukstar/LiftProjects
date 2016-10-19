package code {

  import org.squeryl.KeyedEntity


  class Author(
    val id: Long,
    val firstName: String,
    var lastName: String,
    val email: Option[String]
  ) extends KeyedEntity[Long] {
    def this() = this(0,"","",Some(""))
  }
}
