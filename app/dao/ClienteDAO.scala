package dao

import com.google.inject.ImplementedBy
import models.Cliente
import scala.concurrent.Future

@ImplementedBy(classOf[ClienteDAOImpl])
trait ClienteDAO {

  def get(id: Long): Future[Option[Cliente]]
  def listAll(): Future[Seq[Cliente]]
  def add(cliente: Cliente): Future[String]
  def delete(id: Long): Future[Int]
  def update(id: Long, cliente: Cliente): Future[Int]

}