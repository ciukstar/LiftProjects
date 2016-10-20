package code.music {

  import org.squeryl.KeyedEntity
  import code._
  import MyPrimitiveTypeMode._
  import MusicDb._

  class Song(val id: Long, var title: String, var artistId: Long) extends KeyedEntity[Long] {
    def artist =
      artists.where(a => a.id === artistId).single
  }
}
