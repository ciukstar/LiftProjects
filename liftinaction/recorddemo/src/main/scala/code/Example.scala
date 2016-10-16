package code {

  import net.liftweb.record.field.{ LongField, OptionalEmailField, StringField }
  import net.liftweb.record.{ MetaRecord, Record }

  class Example extends Record[Example] {
    override def meta = Example

    val name = new StringField(this, "") {
      override def validations =
        valMinLen(5, "Must be more than 5 characters") _ :: super.validations
    }
    val funds = new LongField(this)
    val email = new OptionalEmailField(this, 100)
  }

  object Example extends Example with MetaRecord[Example]
}
