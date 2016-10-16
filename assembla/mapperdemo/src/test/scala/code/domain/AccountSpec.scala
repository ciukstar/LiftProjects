package code.domain {
  import java.util.Date
  import org.scalatest._
  import net.liftweb.mapper._

  class AccountSpec extends FlatSpec
    with Matchers with OneInstancePerTest with BeforeAndAfterEach {

    "Account.findAll(By_>)" should "fetch only accounts that have a balance greater than zero" in {

      val now = new Date();

      val a1 = Account.create.balance(100).due(now); a1.save
      val a2 = Account.create.balance(-200).due(now); a2.save

      val accs = Account.findAll(By_>(Account.balance, 0), OrderBy(Account.balance, Descending))

      accs should contain only (a1)

    }

    override def beforeEach: Unit = {
      Account.bulkDelete_!!()
    }

  }
}
