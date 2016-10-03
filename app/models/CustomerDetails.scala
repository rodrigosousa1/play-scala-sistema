package models

import play.api.libs.json._

case class CustomerDetails(id: Long = 0L, name: String, cnpj: String, registration: String, phones: Seq[Phone])

object CustomerDetails {

  implicit val CustomerDetailsFormat = Json.format[CustomerDetails]

}

