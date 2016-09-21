package dao

import com.google.inject.ImplementedBy
import models.Phone
import scala.concurrent.Future

@ImplementedBy(classOf[PhoneDAOImpl])
trait PhoneDAO {
  def getById(id: Long): Future[Option[Phone]]
  def getAll(): Future[Seq[Phone]]
  def save(phone: Phone): Future[Long]
  def delete(id: Long): Future[Int]
  def update(id: Long, phone: Phone): Future[Int]
}