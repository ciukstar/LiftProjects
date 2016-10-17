package code {

  import net.liftweb.record.field.{ LongField, StringField }
  import net.liftweb.record.{MetaRecord, Record}
  import net.liftweb.squerylrecord.KeyedRecord

  class Book private () extends Record[Book] with KeyedRecord[Long] {
    override def meta = Book
    override val idField = new LongField(this, 1)
    val title = new StringField(this, "")
  }

  object Book extends Book with MetaRecord[Book]
}
