package models

import play.api.data._
import play.api.data.Forms._

case class CustomerFormData(name: String, cnpj: Long, registration: Long, phones: Seq[Option[Phone]])

object CustomerForm {

  val form: Form[(CustomerFormData)] = Form(
    mapping(
      "name" 		 -> nonEmptyText,
      "cnpj" 		 -> longNumber,
      "registration" -> longNumber,
      "phones" 		 -> seq(optional(
        mapping(
        	"customerId" ->  default(longNumber, 0L),
        	"number"	 ->  longNumber,
        	"id" 		 ->  default(longNumber, 0L)
        )(Phone.apply)(Phone.unapply)
       ))
    )(CustomerFormData.apply)(CustomerFormData.unapply)
  )

  def toCustomerFormData(data: (Customer, Seq[Option[Phone]])) = {
  	val customer = data._1
  	val phone = data._2

  	CustomerFormData(customer.name, customer.cnpj, customer.registration, phone)
  }
}
