package service

import javax.inject._
import dao.ClienteDAO
import models.Cliente
import scala.concurrent.Future

@Singleton
class ClienteServiceImpl @Inject() (clienteDAO: ClienteDAO) extends ClienteService {

  def getCliente(id: Long): Future[Option[Cliente]] = {
    clienteDAO.get(id)
  }

  def listAllClientes(): Future[Seq[Cliente]] = {
    clienteDAO.listAll
  }

  def addCliente(cliente: Cliente): Future[String] = {
    clienteDAO.add(cliente)
  }

  def deleteCliente(id: Long): Future[Int] = {
    clienteDAO.delete(id)
  }

  def updateCliente(id: Long, cliente: Cliente): Future[Int] = {
    clienteDAO.update(id, cliente)
  }
}
