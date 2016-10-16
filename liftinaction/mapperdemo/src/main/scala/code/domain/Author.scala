package code.domain {

  import net.liftweb.mapper.{CreatedUpdated, IdPK, LongKeyedMapper, LongKeyedMetaMapper, ManyToMany, MappedEmail, MappedEnum, MappedString}

  class Author extends LongKeyedMapper[Author]
    with CreatedUpdated with IdPK with ManyToMany {
    override def getSingleton = Author
    object title extends MappedEnum(this, Titles) {
      override def dbNotNull_? = true
      override def dbIndexed_? = true
    }
    object firstName extends MappedString(this, 255) {
      override def dbNotNull_? = true
      override def dbIndexed_? = false
    }
    object lastName extends MappedString(this, 255) {
      override def dbNotNull_? = true
      override def dbIndexed_? = false
    }
    object email extends MappedEmail(this, 150)
    object books extends MappedManyToMany(BookAuthors, BookAuthors.author, BookAuthors.book, Book)
  }

  object Author extends Author with LongKeyedMetaMapper[Author] {
    override def dbTableName = "authors"
  }

  object Titles extends Enumeration {
    val Mr, Mrs, Miss, Dr = Value
  }
}
