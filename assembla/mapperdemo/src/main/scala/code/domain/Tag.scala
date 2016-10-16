package code.domain {

  import net.liftweb.mapper.{IdPK, LongKeyedMapper, LongKeyedMetaMapper, ManyToMany, MappedLongForeignKey, MappedLongIndex, MappedString}

  class Tag extends LongKeyedMapper[Tag] with ManyToMany {
    override def getSingleton = Tag
    override def primaryKeyField = id
    object id extends MappedLongIndex(this)
    object tag extends MappedString(this, 10)
    object posts extends MappedManyToMany(PostTags, PostTags.tag, PostTags.post, Post)
  }

  object Tag extends Tag with LongKeyedMetaMapper[Tag] {}

  class PostTags extends LongKeyedMapper[PostTags] with IdPK {
    override def getSingleton = PostTags
    object post extends MappedLongForeignKey(this, Post)
    object tag extends MappedLongForeignKey(this, Tag)
  }

  object PostTags extends PostTags with LongKeyedMetaMapper[PostTags] {}
}
