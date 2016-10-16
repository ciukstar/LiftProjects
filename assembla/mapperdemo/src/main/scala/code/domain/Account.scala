package code.domain {
  import net.liftweb.mapper._
  import java.util.Date

  class Account extends LongKeyedMapper[Account] {
    override def getSingleton = Account
    override def primaryKeyField = id

    object id extends MappedLongIndex(this)
    object balance extends MappedLong(this)
    object due extends MappedDate(this)
  }

  object Account extends Account with LongKeyedMetaMapper[Account] {
  }
}
