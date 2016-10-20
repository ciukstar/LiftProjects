package code.music {

  import code._
  import MyPrimitiveTypeMode._
  import org.squeryl.KeyedEntity

  class Artist(val id: Long, val name: String) extends KeyedEntity[Long] {
    def songs =
      from(MusicDb.songs)(s => where(s.artistId === id) select(s))
  }
}
