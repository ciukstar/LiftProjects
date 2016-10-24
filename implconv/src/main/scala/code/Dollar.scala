package code {

  case class Dollar(val value: Long) {
    def + (o: Dollar) = Dollar(value + o.value)
  }

  object Dollar {
    implicit def dollarToEuro(d: Dollar) = Euro(d.value / 2)
  }
}
