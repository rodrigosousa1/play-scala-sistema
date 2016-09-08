package service

import com.google.inject.ImplementedBy
import models.Cliente
import scala.concurrent.Future

@ImplementedBy(classOf[ClienteServiceImpl])
trait ClienteService {

  def getCliente(id: Long): Future[Option[Cliente]]
  def listAllClientes(): Future[Seq[Cliente]]
  def addCliente(cliente: Cliente): Future[String]
  def deleteCliente(id: Long): Future[Int]
  def updateCliente(id: Long, cliente: Cliente): Future[Int]

}
