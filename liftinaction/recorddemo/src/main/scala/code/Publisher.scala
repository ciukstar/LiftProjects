package code {

  import net.liftweb.record.field.{ LongField, StringField }
  import net.liftweb.record.{MetaRecord, Record}
  import net.liftweb.squerylrecord.KeyedRecord
  import org.squeryl.annotations.Column

  class Publisher private () extends Record[Publisher] with KeyedRecord[Long] {
    override def meta = Publisher
    @Column(name = "id")
    override def idField = new LongField(this, 1)
    val name = new StringField(this, "")
  }

  object Publisher extends Publisher with MetaRecord[Publisher]
}
