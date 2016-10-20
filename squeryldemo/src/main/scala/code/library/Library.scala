package code.library {

  import org.squeryl.{ Schema, Session }
  import code._
  import MyPrimitiveTypeMode._

  object Library extends Schema()(MyPrimitiveTypeMode) {
    
    val authors = table[Author]("AUTHORS")
    val books = table[Book]
    val borrowals = table[Borrowal]

    override def columnNameFromPropertyName(propertyName: String) =
      NamingConventionTransforms.snakify(propertyName)

    override def tableNameFromClassName(tableName: String) =
      NamingConventionTransforms.snakify(tableName)

    override def drop = {
      Session.cleanupResources
      super.drop
    }

    override def printDdl = printDdl(println(_))
  }
}
