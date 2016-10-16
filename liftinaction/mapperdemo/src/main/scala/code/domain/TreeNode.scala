package code.domain {

  import net.liftweb.mapper.{CreatedUpdated, IdPK, LongKeyedMapper, LongKeyedMetaMapper, MappedLongForeignKey, MappedString, OneToMany}

  class TreeNode extends LongKeyedMapper[TreeNode] with CreatedUpdated with IdPK with OneToMany[Long, TreeNode] {
    override def getSingleton = TreeNode
    object name extends MappedString(this, 30)
    object parent extends MappedLongForeignKey(this, TreeNode)
    object children extends MappedOneToMany(TreeNode, TreeNode.parent)
  }

  object TreeNode extends TreeNode with LongKeyedMetaMapper[TreeNode] {
    override def dbTableName = "tree"
  }
}
