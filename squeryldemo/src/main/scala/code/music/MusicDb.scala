package code.music {

  import org.squeryl.Schema
  import code._
  import MyPrimitiveTypeMode._

  object MusicDb extends Schema()(MyPrimitiveTypeMode) {
    val artists = table[Artist]
    val songs = table[Song]
  }
}
