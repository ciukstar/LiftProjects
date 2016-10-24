package code {

  case class Euro(val value: Long) {
    def + (o: Euro) = Euro(value + o.value)
  }

  object Euro {
    implicit def euro2dollars(e: Euro) = Dollar(2 * e.value)
  }
}
