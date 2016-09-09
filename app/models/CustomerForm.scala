package models

import play.api.data._
import play.api.data.Forms._

case class CustomerFormData(razaoSocial: String, cnpj: Long, inscricaoMunicipal: Long)

object CustomerForm {

  val form = Form(
    mapping(
      "razaoSocial" -> nonEmptyText,
      "cnpj" -> longNumber,
      "inscricaoMunicipal" -> longNumber
    )(CustomerFormData.apply)(CustomerFormData.unapply)
  )
}
