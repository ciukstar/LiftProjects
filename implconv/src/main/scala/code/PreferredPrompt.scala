package code {

  class PreferredPromt(val preference: String)

  object Greeter {
    def greet(name: String)(implicit promt: PreferredPromt): String = {
      "Welcome, %s. The system is ready.%n%s".format(name, promt.preference)
    }
  }
}
