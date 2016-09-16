package dao

import com.google.inject.ImplementedBy
import models.{ Customer, Phone }
import scala.concurrent.Future

@ImplementedBy(classOf[CustomerDAOImpl])
trait CustomerDAO {

  def get(id: Long): Future[Option[(Customer, Seq[Option[Phone]])]]
  def listAll(): Future[Seq[(Customer, Seq[Option[Phone]])]]
  def add(customer: Customer): Future[Long]
  def delete(id: Long): Future[Int]
  def update(id: Long, customer: Customer): Future[Int]

  def add(phone: List[Phone]): Future[Option[Int]]

  def addCustomerWithPhone(customer: Customer, phones: Seq[Phone]): Future[Option[Int]]
  def updateCustomerWithPhone(id: Long, customer: Customer, phones: Seq[Phone]): Future[Int]

}