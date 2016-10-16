package code.domain {

  import org.scalatest._

  class TagSpec extends FlatSpec
    with Matchers with OneInstancePerTest with BeforeAndAfterAll with BeforeAndAfterEach {

    behavior of "Posts and Tags and PostTgs"

    ignore should "be saved" in {

      val t1 = Tag.create.tag("Tag1")
      val t2 = Tag.create.tag("Tag2")

      val p1 = Post.create.title("Title1").contents("Contents1").published(true)
      val p2 = Post.create.title("Title2").contents("Contents2").published(true)

      p1.save; t1.save
      p2.save; t2.save

      p1.tags += t1
      p1.tags += t2
      p2.tags += t1
      p2.tags += t2

      p1.tags.save
      p1.tags.save

      Tag.findAll() should contain only (t1, t2)
      PostTags.findAll() should have('size(4))

      p1.tags.toList should contain only (t1, t2)
      p2.tags.toList should contain only (t1, t2)
    }

  }
}
