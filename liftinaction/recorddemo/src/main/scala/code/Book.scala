package code {

  import net.liftweb.record.field.{ IntField, LongField, StringField }
  import net.liftweb.record.{MetaRecord, Record}
  import net.liftweb.squerylrecord.KeyedRecord
  import org.squeryl.annotations.Column

  class Book private () extends Record[Book] with KeyedRecord[Long] {
    override def meta = Book

    @Column(name="id")
    override val idField = new LongField(this, 1)
    val pulisherId = new LongField(this, 0)
    val authorId = new LongField(this, 0)
    val title = new StringField(this, "")
    val publishedInYear = new IntField(this, 1990)
  }

  object Book extends Book with MetaRecord[Book]
}
