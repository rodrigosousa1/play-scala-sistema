package models

import play.api.data._
import play.api.data.Forms._

case class ClienteFormData(razaoSocial: String, cnpj: Long, inscricaoMunicipal: Long)

object ClienteForm {

  val form = Form(
    mapping(
      "razaoSocial" -> nonEmptyText,
      "cnpj" -> longNumber,
      "inscricaoMunicipal" -> longNumber
    )(ClienteFormData.apply)(ClienteFormData.unapply)
  )
}