package code.music {

  import org.scalatest._
  import code._
  import MyPrimitiveTypeMode._
  import MusicDb._

  class ArtistSpec extends FlatSpec with Matchers with DatabaseInit
    with OneInstancePerTest with BeforeAndAfterAll with BeforeAndAfterEach {

    "Song.artist foreign keys" should "be among artists" in {

      val a1 = new Artist(1, "Artist1")
      val a2 = new Artist(2, "Artist2")

      inTransaction {
        artists.insert(Seq(a1, a2))
      }

      val s11 = new Song(14, "Song11", a1.id)
      val s12 = new Song(15, "Song12", a1.id)

      val s21 = new Song(16, "Song21", a2.id)
      val s22 = new Song(17, "Song22", a2.id)

      inTransaction {
        songs.insert(Seq(s11, s12, s21, s22))
      }

      inTransaction {
        val allArtists = from(artists)(a => where(1 === 1) select (a)).seq
        val allSongs = from(songs)(s => where(1 === 1) select (s)).seq

        allArtists should have('size(2))
        allSongs should have('size(4))

        allSongs.map(_.artist.id) should be(allArtists.map(_.id))
      }

    }

    override def beforeAll(): Unit = {
      configureDb()
      inTransaction {
        MusicDb.create
      }
    }

    override def beforeEach(): Unit = {
      inTransaction {
        artists.deleteWhere(a => 1 === 1)
      }
    }

    override def afterAll(): Unit = {
      closeDbConnection()
    }

  }
}
