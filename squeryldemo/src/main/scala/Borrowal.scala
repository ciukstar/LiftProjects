package code {

  import java.sql.Timestamp

  class Borrowal(
    val id: Long,
    val bookId: Long,
    val borrowerAccountId: Long,
    val returnedOn: Option[Timestamp],
    val numberOfPhonecallsForReturn: Int
  )
}
