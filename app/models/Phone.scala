package models

import play.api.libs.json._

case class Phone(customerId: Long, number: String, id: Long = 0L)

object Phone {

  implicit val phoneFormat = Json.format[Phone]

  def tupled = (this.apply _).tupled

}

