package code {

  import org.squeryl.Schema

  object Bookstore extends Schema {
    val authors = table[Author]("authors")
    val books = table[Book]("books")
    val publishers = table[Publisher]("publishers")
  }
}
