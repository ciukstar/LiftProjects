package code.music {

  import org.squeryl.{ KeyedEntityDef, Schema }
  import code.MyPrimitiveTypeMode._

  object Music extends Schema {

    implicit object ArtistKED extends KeyedEntityDef[Artist, Long] {
      override def getId(a: Artist) = a.id
      override def idPropertyName = "id"
      override def isPersisted(a: Artist) = a.id > 0
    }

    implicit object SongKED extends KeyedEntityDef[Song, Long] {
      override def getId(s: Song) = s.id
      override def idPropertyName = "id"
      override def isPersisted(s: Song) = s.id > 0
    }

    val artists = table[Artist]
    val songs = table[Song]
  }
}
