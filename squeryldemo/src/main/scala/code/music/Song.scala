package code.music {

  import org.squeryl.{ Query }
  import MusicPrimitiveTypeMode._
  import Music._

  class Song(val title: String, val artistId: Long) {
    def this(title: String, artist: Artist) = this(title, artist.id)

    val id: Long = -1

    def artist: Query[Artist] =
      artists.where(a => a.id === artistId)
  }
}
