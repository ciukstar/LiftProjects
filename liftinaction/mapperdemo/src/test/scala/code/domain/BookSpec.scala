package code.domain {

  import java.text.{DateFormat => Df}
  import net.liftweb.db.DB
  import net.liftweb.mapper.{By, ByList, BySql, By_<, IHaveValidatedThisSQL, In, Like, MapperRules, Schemifier}
  import net.liftweb.util.{DefaultConnectionIdentifier, Helpers}
  import org.scalatest._

  class BookSpec extends FlatSpec with Matchers
    with OneInstancePerTest with BeforeAndAfterAll with BeforeAndAfterEach {

    ("In(Book.publisher, Publisher.id, Like(Publisher.name, \"N2%\"))"
      should "fetch books of publishers whose name is like N2") in {

        val b1 = Book.create.title("T1").blurb("B1").publishedOn(Df.getDateInstance.parse("May 1, 2000"))
        val b2 = Book.create.title("T2").blurb("B2").publishedOn(Df.getDateInstance.parse("May 2, 2000"))
        val b3 = Book.create.title("T3").blurb("B3").publishedOn(Df.getDateInstance.parse("May 3, 2000"))
        val b4 = Book.create.title("T4").blurb("B4").publishedOn(Df.getDateInstance.parse("May 4, 2000"))

        val p1 = Publisher.create.name("N1").description("D1").saveMe
        val p2 = Publisher.create.name("N2").description("D2").saveMe

        (p1.books += (b1, b2)).save
        (p2.books += (b3, b4)).save

        val books = Book.findAll(In(Book.publisher, Publisher.id, Like(Publisher.name, "N2%")))

        books should contain only (b3, b4)
      }

    "Book.flatMap(_.publisher.obj.map(_.name)).openOr(\"unknown\")" should "extract the boulisher name of a book" in {
      val p = Publisher.create.name("P1").description("D1").saveMe
      val b1 = Book.create.title("T1").blurb("B1").publishedOn(Df.getDateInstance.parse("May 1, 2015")).saveMe
      val b2 = Book.create.title("T1").blurb("B1").publishedOn(Df.getDateInstance.parse("May 1, 2015")).saveMe

      (p.books += (b1, b2)).save

      val choosen = Book.find(By(Book.title, b2.title.get))

      val publisherName = choosen.flatMap(_.publisher.obj.map(_.name)).openOr("unknown")

      publisherName should be(p.name.get)
    }

    "Book.finAll(BySql('SQL')) with params" should "fetch only Books constraint by WHERE clause with params" in {

      val b1 = Book.create.title("T1").blurb("B1").publishedOn(Df.getDateInstance.parse("May 1, 2014")).saveMe
      val b2 = Book.create.title("T2").blurb("B2").publishedOn(Df.getDateInstance.parse("May 2, 2014")).saveMe
      val b3 = Book.create.title("T3").blurb("B3").publishedOn(Df.getDateInstance.parse("May 3, 2014")).saveMe
      val b4 = Book.create.title("T4").blurb("B4").publishedOn(Df.getDateInstance.parse("May 4, 2014")).saveMe

      val books = Book.findAll(BySql(
        "published_on BETWEEN ? AND ?",
        IHaveValidatedThisSQL("sstarciuc", "2016-10-16"),
        b2.publishedOn.get, b4.publishedOn.get
      ))

      books should contain only (b2, b3, b4)

    }

    "Book.findAll(BySql('sql'))" should "fetch only Books constraint by WHERE clause" in {

      val b1 = Book.create.title("T1").blurb("B1").publishedOn(Df.getDateInstance.parse("March 1, 2015")).saveMe
      val b2 = Book.create.title("T2").blurb("B2").publishedOn(Df.getDateInstance.parse("March 2, 2015")).saveMe
      val b3 = Book.create.title("T3").blurb("B3").publishedOn(Df.getDateInstance.parse("March 3, 2015")).saveMe

      val books = Book.findAll(BySql(
        "published_on < TO_DATE('03.03.2015', 'DD.MM.YYYY')",
        IHaveValidatedThisSQL("sstarciuc", "2016-10-16")
      ))

      books should contain only (b1, b2)
    }

    "Book.findAll(By_<(MappedField, value))" should "retrive only books whose field values are less than value" in {

      val b1 = Book.create.title("T1").blurb("B1").publishedOn(Df.getDateInstance.parse("February 1, 2016")).saveMe
      val b2 = Book.create.title("T2").blurb("B2").publishedOn(Df.getDateInstance.parse("February 2, 2016")).saveMe
      val b3 = Book.create.title("T3").blurb("B3").publishedOn(Df.getDateInstance.parse("February 3, 2016")).saveMe

      val bs = Book.findAll(By_<(Book.publishedOn, b3.publishedOn.get))

      bs should contain only (b1, b2)
    }

    "Book.findAll(ByList(MappedField, List[_]))" should "find books whose field value is in the list" in {

      val b1 = Book.create.title("T1").blurb("B1").publishedOn(Df.getDateInstance().parse("January 1, 2013")).saveMe
      val b2 = Book.create.title("T2").blurb("B2").publishedOn(Df.getDateInstance().parse("January 2, 2013")).saveMe
      val b3 = Book.create.title("T3").blurb("B3").publishedOn(Df.getDateInstance().parse("January 3, 2013")).saveMe

      val bs = Book.findAll(ByList(Book.title, b2.title.get :: b3.title.get :: Nil))
      bs should not contain b1
      bs should contain only (b2, b3)
    }

    "Book.find(By(MappedField))" should "fetch Books which satisfy given constraints" in {

      val b1 = Book.create.title("Book1").blurb("Blurb1").publishedOn(Df.getDateInstance.parse("March 1, 2000")).saveMe
      val b2 = Book.create.title("Book2").blurb("Blurb2").publishedOn(Df.getDateInstance.parse("March 2, 2000")).saveMe
      val b3 = Book.create.title("Book3").blurb("Blurb3").publishedOn(Df.getDateInstance.parse("March 3, 2000")).saveMe

      Book.find(By(Book.title, b1.title.get)) openOrThrowException "fail()" should be(b1)
      Book.find(By(Book.publishedOn, b2.publishedOn.get)) openOrThrowException "fail()" should be(b2)
    }

    "You" should "save the Publisher if you want to be fetched along a Book" in {

      val bookTitle = "Lift in Action"
      val publisherName = "Manning"
      val manning = Publisher.create.name(publisherName).saveMe

      manning.books += Book.create.title(bookTitle)
      manning.books.save

      val books = Book.findAll
      books should have('size(1))
      val book = books.head
      book.title should be(bookTitle)
      val p = (book.publisher.obj openOrThrowException "Not found")
      p.name.get should be(publisherName)

    }

    "Book.save" should "save a Book and its relationship to the Publisher" in {
      val manning = Publisher.create.name("Manning").saveMe
      val liftInAction = Book.create.title("Lift in Action").publisher(manning).saveMe

      val books = Book.findAll()
      books should contain only (liftInAction)
      books.head.publisher should be(manning)
    }

    override def beforeAll(): Unit = {
      DB.defineConnectionManager(DefaultConnectionIdentifier, DbVendor)
      MapperRules.columnName = (_, name) => Helpers.snakify(name)
      MapperRules.tableName = (_, name) => Helpers.snakify(name)
      Schemifier.schemify(true, Schemifier.infoF _, Publisher, Book, BookAuthors, Author)
    }
    override def afterEach(): Unit = {
      BookAuthors.bulkDelete_!!()
      Book.bulkDelete_!!()
      Publisher.bulkDelete_!!()
      Author.bulkDelete_!!()
    }
    override def afterAll(): Unit = {
      DbVendor.tearDown
    }
  }
}

