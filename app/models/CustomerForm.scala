package models

import play.api.data._
import play.api.data.Forms._


object CustomerForm {

  val form: Form[Customer] = Form(
    mapping(
      "razaoSocial" -> nonEmptyText,
      "cnpj" -> longNumber,
      "inscricaoMunicipal" -> longNumber,
      "id" -> default(longNumber, 0L)
    )(Customer.apply)(Customer.unapply)
  )
}
