package code.domain {

  import net.liftweb.mapper.{ Ascending, CreatedUpdated, IdPK, LongKeyedMapper, LongKeyedMetaMapper, MappedString, MappedText, OneToMany, OrderBy }

  class Publisher extends LongKeyedMapper[Publisher]
    with CreatedUpdated with IdPK with OneToMany[Long, Publisher] {

    override def getSingleton = Publisher
    object name extends MappedString(this, 255)
    object description extends MappedText(this)
    object books extends MappedOneToMany(Book, Book.publisher, OrderBy(Book.title, Ascending))
  }

  object Publisher extends Publisher with LongKeyedMetaMapper[Publisher] {
    override def dbTableName = "publishers"
  }
}
