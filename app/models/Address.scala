package models

import play.api.libs.json._

case class Address(customerId: Long, street: String, neighborhood: String, city: String, state: String, cep: String, id: Long = 0L)

object Address {

  implicit val addressFormat = Json.format[Address]

  def tupled = (this.apply _).tupled
  def default() = Address(0L, "", "", "", "", "", 0L)

}