package code.domain

import org.scalatest._

class CommentSpec extends FlatSpec
  with Matchers with OneInstancePerTest with BeforeAndAfterEach {

  "Comment.findAll" should "fetch the Comment and related Post" in {
    val p = Post.create.title("T1").contents("C1").published(true).saveMe
    val c = Comment.create.post(p).author("Author").comment("Comment1").saveMe

    val cs = Comment.findAll

    cs should have('size(1))
    (cs.head.post.obj openOrThrowException "Post not found") should be(p)
  }

  override def afterEach: Unit = {
    Comment.bulkDelete_!!()
    Post.bulkDelete_!!()
  }

}
