package dao

import com.google.inject.ImplementedBy
import models.Address
import scala.concurrent.Future

@ImplementedBy(classOf[AddressDAOImpl])
trait AddressDAO {
  def getById(id: Long): Future[Option[Address]]
  def getAll(): Future[Seq[Address]]
  def save(phone: Address): Future[Long]
  def delete(id: Long): Future[Int]
  def update(id: Long, phone: Address): Future[Int]
}