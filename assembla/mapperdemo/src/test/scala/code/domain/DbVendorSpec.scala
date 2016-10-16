package code.domain {
  import org.scalatest._
  import net.liftweb.util.DefaultConnectionIdentifier

  class DbVendorSpec extends FlatSpec with Matchers with OneInstancePerTest with BeforeAndAfterEach {
   
    override def beforeEach = ()
    override def afterEach {
      DbVendor.tearDown
    }

    "DbVendor.releaseConnection(c)" should "should return the connection to the pool" in {
      
      val c = DbVendor.newConnection(DefaultConnectionIdentifier)
      DbVendor.releaseConnection(c openOrThrowException "No connection")

      DbVendor.pool.size should be (1)
    }


    "DbVendor.tearDown" should "close all pooled connections and dispose the pool" in {
      val c = DbVendor.newConnection(DefaultConnectionIdentifier)
      DbVendor.tearDown
      
      DbVendor.pool shouldBe empty
    }
  }
}
