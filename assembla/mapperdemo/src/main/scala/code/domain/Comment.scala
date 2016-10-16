package code.domain {
  import net.liftweb.mapper._

  class Comment extends LongKeyedMapper[Comment] {
    override def getSingleton = Comment
    override def primaryKeyField = id

    object id extends MappedLongIndex(this)
    object post extends MappedLongForeignKey(this, Post)
    object author extends MappedString(this, 40)
    object comment extends MappedString(this, 140)
  }

  object Comment extends Comment with LongKeyedMetaMapper[Comment] {}
}
