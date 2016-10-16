package code.domain {
  import org.scalatest._
  import net.liftweb.mapper.{By}

  class PostSpec extends FlatSpec
    with Matchers with OneInstancePerTest with BeforeAndAfterEach {

    "Sving the post" should "save its comments" in {
      val p = Post.create.title("T1").contents("C1").published(true)
      val c1 = Comment.create.author("Auth1").comment("Comment1")
      val c2 = Comment.create.author("Auth2").comment("Comment2")

      val cs = p.comments.toList
      p.comments += (c1, c2)
      p.save

      Comment.findAll should contain only (c1, c2)
    }

    "Post.findAll(QueryParam[Post])" should "fetch only posts taht satisfy the predicate" in {
      val p1 = Post.create.title("T1").contents("C1").published(true); p1.save
      val p2 = Post.create.title("T2").contents("C2").published(false); p2.save

      Post.findAll(By(Post.published, false)) should contain only (p2)
    }

    "Post.findAllFields(Seq[SelectableField])" should "should fetch only specified fields" in {
      val p = Post.create.title("Title1").contents("Contents1").published(true);
      p.save

      val posts: List[Post] = Post.findAllFields(Seq(Post.title))

      posts.map(_.title) should contain(p.title)
      posts.map(_.contents) should not be p.contents
    }

    "Post.findAll" should "fetch all posts" in {
      val p1 = Post.create
        .title("My First Blog Post")
        .contents("This is first very short blog post but it will have to do for now.")
        .published(true)

      val p2 = Post.create
        .title("My Second Blog Post")
        .contents("This is second very short blog post but it eill have to do for now.")
      p1.save
      p2.save

      Post.findAll should contain allOf (p1, p2)
    }

    "Post.save" should "perist a post" in {
      val p = Post.create.title("My First Blog Post").contents("Blog contents").published(true)
      p.save

      Post.findByKey(p.id.get) should be(p)
    }

    override def afterEach: Unit = {
      Post.bulkDelete_!!()
    }

  }
}
