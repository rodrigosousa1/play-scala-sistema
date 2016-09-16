package models

import play.api.data._
import play.api.data.Forms._

case class CustomerFormData(customer: Customer, phones: Seq[Option[Phone]])

object CustomerForm {

  val form: Form[(CustomerFormData)] = Form(
    mapping(
      "customer" -> mapping(
        "name" -> nonEmptyText,
        "cnpj" -> longNumber,
        "registration" -> longNumber,
        "id" -> default(longNumber, 0L))(Customer.apply)(Customer.unapply),

      "phones" -> seq(optional(
        mapping(
          "customerId" -> default(longNumber, 0L),
          "number" -> longNumber,
          "id" -> default(longNumber, 0L))(Phone.apply)(Phone.unapply))))(CustomerFormData.apply)(CustomerFormData.unapply))

  def toCustomerFormData(data: (Customer, Seq[Option[Phone]])) = {
    val customer = data._1
    val phone = data._2

    CustomerFormData(customer, phone)
  }
}
