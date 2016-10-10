package models

import play.api.libs.json._

case class Item(quoteId: Long, quantity: Int, description: String, price: Double, total: Double, id: Long = 0L)

object Item {

  implicit val itemFormat = Json.format[Item]

  def tupled = (this.apply _).tupled

}
