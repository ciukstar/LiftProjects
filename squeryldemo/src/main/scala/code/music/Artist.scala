package code.music {

  import code.PrimitiveTypeMode._
  import org.squeryl.{ Query }

  class Artist(val name: String) {
    val id: Long = -1

    def songs: Query[Song] =
      from(Music.songs)(s => where(s.artistId === id) select(s))
  }
}
