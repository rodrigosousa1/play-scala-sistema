package models

import play.api.libs.json._

case class Customer(name: String, cnpj: String, registration: String, id: Long = 0L)

object Customer {

  implicit val customerFormat = Json.format[Customer]

  def tupled = (this.apply _).tupled

}
