package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import service.ClienteService
import models.{ Cliente, ClienteForm }
import play.api.i18n.{MessagesApi, Messages, I18nSupport}
import play.api.libs.concurrent.Execution.Implicits._

@Singleton
class ClienteController @Inject() (cs: ClienteService, val messagesApi: MessagesApi) extends Controller with I18nSupport {


  def newCliente() = Action { implicit request =>
    Ok(views.html.customer.newCustomer(ClienteForm.form))
  }

  def getCliente(id: Long) = Action.async { implicit request =>
    val cliente = cs.getCliente(id)
    cliente.map { res =>
      Ok("Got result: " + res.getOrElse("Not Found"))
    }
  }

  def listAllClientes() = Action.async { implicit request =>
    val clientes = cs.listAllClientes
    clientes.map { clientes =>
      Ok(views.html.customer.list(clientes))
    }
  }

  def addCliente() = Action.async { implicit request =>
    val cliente = Cliente(0L, "NOVO TESTE 2", 4231487945258L, 3254698766L)
    val insert = cs.addCliente(cliente)
    insert.map(Ok(_))
  }

  def deleteCliente(id: Long) = Action.async { implicit request =>
    val delete = cs.deleteCliente(id)
    delete.map(res => Ok("Success"))

  }

  def updateCliente(id: Long) = Action.async { implicit request =>
    val update = cs.updateCliente(id, Cliente(id, "Updated", 5555555555555L, 333333333L))
    update.map(res => Ok("Updated"))
  }

}
