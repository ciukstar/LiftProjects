package code {

  import net.liftweb.record.field.{ LongField, OptionalDateTimeField, OptionalIntField, StringField }
  import net.liftweb.record.{MetaRecord, Record}
  import net.liftweb.squerylrecord.KeyedRecord
  import org.squeryl.annotations.Column

  class Author private () extends Record[Author] with KeyedRecord[Long] {
    override def meta = Author

    @Column(name = "id")
    override val idField = new LongField(this, 1)
    val name = new StringField(this, "")
    val age = new OptionalIntField(this)
    // val birthday = new OptionalDateTimeField(this)
  }

  object Author extends Author with MetaRecord[Author]
}
