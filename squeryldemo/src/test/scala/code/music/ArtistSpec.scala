package code.music {

  import org.scalatest._
  import code._
  import MyPrimitiveTypeMode._
  import Music._

  class ArtistSpec extends FlatSpec with Matchers with DatabaseInit
    with OneInstancePerTest with BeforeAndAfterAll with BeforeAndAfterEach {

    "Song.artist foreign keys" should "be among artists" in {

      inTransaction {
        val a1 = artists.insert(new Artist("Artist1"))
        val a2 = artists.insert(new Artist("Artist2"))
        val s11 = songs.insert(new Song("Song11", a1))
        val s12 = songs.insert(new Song("Song12", a1))
        val s21 = songs.insert(new Song("Song21", a2))
        val s22 = songs.insert(new Song("Song22", a2))
      }

      inTransaction {
        val allArtists = from(artists)(a => where(1 === 1) select (a)).seq
        val allSongs = from(songs)(s => where(1 === 1) select (s)).seq

        allArtists should have('size(2))
        allSongs should have('size(4))

        allSongs.map(_.artist.single.id) should be(allArtists.map(_.id))
      }

    }

    override def beforeAll(): Unit = {
      configureDb()
      inTransaction {
        Music.create
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
