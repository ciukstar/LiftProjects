package code.domain {

  import net.liftweb.mapper.{DB, Schemifier}
  import net.liftweb.util.DefaultConnectionIdentifier
  import org.scalatest.{ BeforeAndAfterAll, BeforeAndAfterEach, Suites }

  class MapperSuite extends Suites(
    new AccountSpec,
    new CommentSpec,
    new PostSpec,
    new TagSpec
  ) with BeforeAndAfterAll with BeforeAndAfterEach {

    override def beforeAll(): Unit = {
      DB.defineConnectionManager(DefaultConnectionIdentifier, DbVendor)
      Schemifier.schemify(true, Schemifier.infoF _, Account, Comment, Post, Tag, PostTags)
    }

    override def afterEach(): Unit = {
      Account.bulkDelete_!!()
      Comment.bulkDelete_!!()
      PostTags.bulkDelete_!!()
      Post.bulkDelete_!!()
      Tag.bulkDelete_!!()
    }

    override def afterAll: Unit = {
      DbVendor.tearDown
    }
  }
}
