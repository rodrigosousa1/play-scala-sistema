package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import service.CustomerService
import scala.concurrent.Future
import models.{ Customer, CustomerForm }
import play.api.i18n.{ MessagesApi, Messages, I18nSupport }
import play.api.libs.concurrent.Execution.Implicits._

@Singleton
class CustomerController @Inject() (cs: CustomerService, val messagesApi: MessagesApi) extends Controller with I18nSupport {

  def newCustomer() = Action { implicit request =>
    Ok(views.html.customer.newCustomer(CustomerForm.form))
  }

  def getCustomer(id: Long) = Action.async { implicit request =>
    val customer = cs.getCustomer(id)
    customer.map { res =>
      Ok(views.html.customer.details(res.get))
    }
  }

  def listAllCustomers() = Action.async { implicit request =>
    val customers = cs.listAllCustomers
    customers.map { customers =>
      Ok(views.html.customer.list(customers))
    }
  }

  def addCustomer() = Action.async { implicit request =>
    CustomerForm.form.bindFromRequest.fold(
      formWithErrors => {
        Future.successful(BadRequest(views.html.customer.newCustomer(formWithErrors)))
      },
      customerData => {
        val newCustomer = Customer(customerData.razaoSocial, customerData.cnpj, customerData.inscricaoMunicipal)
        cs.addCustomer(newCustomer).map(res =>
          Redirect(routes.CustomerController.newCustomer()).flashing("success" -> Messages("flash.success")))
      })
  }

  def deleteCustomer(id: Long) = Action.async { implicit request =>
    val delete = cs.deleteCustomer(id)
    delete.map(res => Redirect(routes.CustomerController.listAllCustomers))

  }

  def updateCustomer(id: Long) = Action.async { implicit request =>
     CustomerForm.form.bindFromRequest.fold(
      formWithErrors => {
        Future.successful(BadRequest(views.html.customer.newCustomer(formWithErrors, Some(id))))
      },
      customerData => {
        val updatedCustomer = Customer(customerData.razaoSocial, customerData.cnpj, customerData.inscricaoMunicipal, id)
        cs.updateCustomer(id, updatedCustomer).map(res =>
          Redirect(routes.CustomerController.listAllCustomers()).flashing("success" -> Messages("flash.success")))
      })
  }

  def editCustomer(id: Long) = Action.async { implicit request =>
    cs.getCustomer(id).map{ res => 
      val form = CustomerForm.form.fill(res.get)
      Ok(views.html.customer.newCustomer(form, Some(id)))
    }
      

  }

}
