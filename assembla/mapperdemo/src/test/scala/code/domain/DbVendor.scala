package code.domain {
  import java.sql.{Connection, DriverManager}
  import net.liftweb.common.{Box, Full}
  import net.liftweb.util.ConnectionIdentifier
  import net.liftweb.mapper.ConnectionManager

  object DbVendor extends ConnectionManager {
    var pool: List[Connection] = Nil

    private def createOne: Connection = {
      Class.forName("org.h2.Driver")
      val c = DriverManager.getConnection("jdbc:h2:mem:mydb;DB_CLOSE_DELAY=-1")
      c setAutoCommit false; c
    }

    override def newConnection(name: ConnectionIdentifier): Box[Connection] = synchronized {
      pool match {
        case Nil =>
          pool = createOne :: pool
          newConnection(name)
        case x :: xs =>
          pool = xs
          Full(x)
      }
    }

    override def releaseConnection(c: Connection): Unit = synchronized {
      pool = c :: pool
    }

    def tearDown: Unit = synchronized {
      pool.filter(!_.isClosed).foreach(_.close)
      pool = Nil
    }
  }
}
