package code.domain {

  import net.liftweb.mapper.{CreatedUpdated, IdPK, LongKeyedMapper, LongKeyedMetaMapper, ManyToMany, MappedDate, MappedLongForeignKey, MappedString, MappedText}

  class Book extends LongKeyedMapper[Book]
    with CreatedUpdated with IdPK with ManyToMany {
    override def getSingleton = Book
    object title extends MappedString(fieldOwner = this, maxLen = 255)
    object blurb extends MappedText(fieldOwner = this)
    object publishedOn extends MappedDate(fieldOwner = this)
    object publisher extends MappedLongForeignKey(theOwner = this, _foreignMeta = Publisher)
    object authors extends MappedManyToMany(BookAuthors, BookAuthors.book, BookAuthors.author, Author)
  }

  object Book extends Book with LongKeyedMetaMapper[Book] {
    override def dbTableName = "books"
  }

  class BookAuthors extends LongKeyedMapper[BookAuthors] with IdPK {
    override def getSingleton = BookAuthors
    object author extends MappedLongForeignKey(this, Author)
    object book extends MappedLongForeignKey(this, Book)
  }

  object BookAuthors extends BookAuthors with LongKeyedMetaMapper[BookAuthors]
}
