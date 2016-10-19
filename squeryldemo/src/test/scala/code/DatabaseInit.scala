package code {

  import org.slf4j.LoggerFactory
  import com.mchange.v2.c3p0.ComboPooledDataSource
  import org.squeryl.adapters.H2Adapter
  import org.squeryl.{Session, SessionFactory}

  trait DatabaseInit {
    LoggerFactory.getLogger(getClass)

    val user = "root"
    val pwd = ""
    val url = "jdbc:h2:mem:mydb"

    val ds = new ComboPooledDataSource

    def configureDb(): Unit = {
      ds.setDriverClass("org.h2.Driver")
      ds.setJdbcUrl(url)
      ds.setUser(user)
      ds.setPassword(pwd)
      ds.setMinPoolSize(1)
      ds.setAcquireIncrement(1)
      ds.setMaxPoolSize(50)

      SessionFactory.concreteFactory = Some(() => session)

      def session = {
        Session.create(ds.getConnection, new H2Adapter)
      }

    }

    def closeDbConnection(): Unit = {
      ds.close()
    }

  }
}
