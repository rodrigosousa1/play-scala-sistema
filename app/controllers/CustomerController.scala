package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import service.CustomerService
import scala.concurrent.Future
import models.{ Customer, Phone, CustomerDetails }
import play.api.i18n.{ MessagesApi, Messages, I18nSupport }
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json._

@Singleton
class CustomerController @Inject() (cs: CustomerService, val messagesApi: MessagesApi) extends Controller with I18nSupport {

  def getAllCustomers() = Action.async { implicit request =>
    val customers = cs.getAllCustomersDetails
    customers.map { customers =>
      Ok(Json.toJson(customers))
    }
  }

  def getCustomerById(id: Long) = Action.async { implicit request =>
    val customer = cs.getCustomerDetailsById(id)
    customer.map { customer =>
      customer match {
        case Some(x) => Ok(Json.toJson(x))
        case None => NotFound(Json.obj("status" -> "NOT_FOUND"))
      }
    }
  }

  def saveCustomer() = Action.async(BodyParsers.parse.json) { implicit request =>
    request.body.validate[CustomerDetails].fold(
      errors => {
        Future.successful(BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toJson(errors))))
      },
      customer => {
        cs.saveCustomerDetails(customer).map { res =>
          Ok(Json.obj("status" -> "OK"))
        }

      })
  }

  def deleteCustomer(id: Long) = Action.async { implicit request =>
    val delete = cs.deleteCustomer(id)
    delete.map { res =>
      Ok(Json.obj("status" -> "OK"))
    }

  }

  def updateCustomer(id: Long) = Action.async(BodyParsers.parse.json) { implicit request =>
    request.body.validate[CustomerDetails].fold(
      errors => {
        Future.successful(BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toJson(errors))))
      },
      customer => {
        cs.updateCustomerDetails(id, customer).map { res =>
          Ok(Json.obj("status" -> "OK"))
        }

      })

  }
}
