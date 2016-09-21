package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import service.CustomerService
import scala.concurrent.Future
import models.{ Customer, CustomerForm, Phone }
import play.api.i18n.{ MessagesApi, Messages, I18nSupport }
import play.api.libs.concurrent.Execution.Implicits._

@Singleton
class CustomerController @Inject() (cs: CustomerService, val messagesApi: MessagesApi) extends Controller with I18nSupport {

  def newCustomer() = Action { implicit request =>
    Ok(views.html.customer.newCustomer(CustomerForm.form))
  }

  def getById(id: Long) = Action.async { implicit request =>
    val customer = cs.getCustomerWithPhone(id)
    customer.map { res =>
      Ok(views.html.customer.details(res.get))
    }
  }

  def getAll() = Action.async { implicit request =>
    val customers = cs.getAllCustomersWithPhone
    customers.map { customers =>
      Ok(views.html.customer.list(customers))
    }
  }

  def saveCustomer() = Action.async { implicit request =>
    CustomerForm.form.bindFromRequest.fold(
      formWithErrors => {
        Future.successful(BadRequest(views.html.customer.newCustomer(formWithErrors)))
      },
      data => {
        val newCustomer = data.customer
        val newPhones = data.phones.flatMap(_.to[List])
        cs.add(newCustomer, newPhones).map { res =>
          Redirect(routes.CustomerController.newCustomer()).flashing("success" -> Messages("flash.success"))
        }
      })
  }

  def delete(id: Long) = Action.async { implicit request =>
    val delete = cs.deleteCustomer(id)
    delete.map(res => Redirect(routes.CustomerController.getAll))

  }

  def updateCustomerWithPhone(id: Long) = Action.async { implicit request =>
    CustomerForm.form.bindFromRequest.fold(
      formWithErrors => {
        Future.successful(BadRequest(views.html.customer.newCustomer(formWithErrors, Some(id))))
      },
      data => {
        val updatedCustomer = data.customer
        val updatedPhones = data.phones.flatMap(_.to[List])
        cs.update(id, updatedCustomer, updatedPhones).map(res => Redirect(routes.CustomerController.getAll).flashing("success" -> Messages("flash.success")))
      })
  }

  def editCustomer(id: Long) = Action.async { implicit request =>
    cs.getCustomerWithPhone(id).map { res =>
      val formData = CustomerForm.toCustomerFormData(res.get)
      val form = CustomerForm.form.fill(formData)
      Ok(views.html.customer.newCustomer(form, Some(id)))
    }
  }

}
