package code.domain

import net.liftweb.db.DB
import net.liftweb.mapper.{MapperRules, Schemifier}
import net.liftweb.util.{DefaultConnectionIdentifier, Helpers}
import org.scalatest._

class TreeNodeSpec extends FlatSpec with Matchers with OneInstancePerTest with BeforeAndAfterAll with BeforeAndAfterEach {

  "TreeNode.findAll" should "fetch all children down recursively" in {

    val n1 = TreeNode.create.name("n1").saveMe
    val n11 = TreeNode.create.name("n11")
    val n12 = TreeNode.create.name("n12")
    val n13 = TreeNode.create.name("n13")
    (n1.children += (n11, n12, n13)).save

    val n111 = TreeNode.create.name("n111")
    val n112 = TreeNode.create.name("n112")
    (n11.children += (n111, n112)).save

    val root = TreeNode.find(n1.id.get) openOrThrowException("fail")

    root.children.toList should contain only (n11, n12, n13)
    root.children.toList.flatMap(_.children) should contain only (n111, n112)
  }

  "TreeNode.find(id)" should "fetch all children of the node" in {

    val r1 = TreeNode.create.name("r1").saveMe
    (r1.children += (
      TreeNode.create.name("c11"),
      TreeNode.create.name("c12")
    )).save

    val r2 = TreeNode.create.name("r2").saveMe
    (r2.children += (
      TreeNode.create.name("c21"),
      TreeNode.create.name("c22"),
      TreeNode.create.name("c23")
    )).save

    val children = (TreeNode.find(r2.id.get) openOrThrowException("fail")).children.toList
    children should have ('size (3))
    children.map(_.name) should contain only ("c21", "c22", "c23")
  }

  override def beforeAll(): Unit = {
    DB.defineConnectionManager(DefaultConnectionIdentifier, DbVendor)
    MapperRules.columnName = (_, name) => Helpers.snakify(name)
    MapperRules.tableName = (_, name) => Helpers.snakify(name)
    Schemifier.schemify(true, Schemifier.infoF _, TreeNode)
  }
  override def afterEach(): Unit = {
    TreeNode.bulkDelete_!!( )
  }
  override def afterAll(): Unit =
    DbVendor.tearDown
}
