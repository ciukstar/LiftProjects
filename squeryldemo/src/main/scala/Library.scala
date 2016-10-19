package code {

  import org.squeryl.{Schema}

  object Library extends Schema()(MyPrimitiveTypeMode) {
    import MyPrimitiveTypeMode._
    val authors = table[Author]("AUTHORS")
    val books = table[Book]
    val borrowals = table[Borrowal]

    override def printDdl = printDdl(println(_))
  }
}
