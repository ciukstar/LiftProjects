package code.domain {
  import net.liftweb.mapper._

  class Post extends LongKeyedMapper[Post] with OneToMany[Long, Post] with ManyToMany {
    override def getSingleton = Post
    override def primaryKeyField = id

    object id extends MappedLongIndex(this)
    object title extends MappedString(this, 140)
    object contents extends MappedText(this)
    object published extends MappedBoolean(this)
    object comments extends MappedOneToMany(Comment, Comment.post, OrderBy(Comment.id, Descending))
    object tags extends MappedManyToMany(PostTags, PostTags.post, PostTags.tag, Tag)
  }

  object Post extends Post with LongKeyedMetaMapper[Post] {}
}
