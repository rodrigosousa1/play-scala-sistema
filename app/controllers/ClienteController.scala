package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import service.ClienteService
import models.Cliente
import play.api.libs.concurrent.Execution.Implicits._

@Singleton
class ClienteController @Inject() (cs: ClienteService) extends Controller {

  def getCliente(id: Long) = Action.async { implicit request =>
    val cliente = cs.getCliente(id)
    cliente.map { res =>
      Ok("Got result: " + res.getOrElse("Not Found"))
    }
  }

  def listAllClientes() = Action.async { implicit request =>
    val clientes = cs.listAllClientes
    clientes.map { clientes =>
      Ok("Got result:" + clientes)
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
