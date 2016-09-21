package dao

import com.google.inject.ImplementedBy
import models.{ Customer, Phone }
import scala.concurrent.Future

@ImplementedBy(classOf[CustomerDAOImpl])
trait CustomerDAO {

  def getById(id: Long): Future[Option[Customer]]
  def getAll(): Future[Seq[Customer]]
  def save(customer: Customer): Future[Long]
  def delete(id: Long): Future[Int]
  def update(id: Long, customer: Customer): Future[Int]


  def addCustomerWithPhone(customer: Customer, phones: Seq[Phone]): Future[Option[Int]]
  def updateCustomerWithPhone(id: Long, customer: Customer, phones: Seq[Phone]): Future[Int]
  def getWithPhone(id: Long): Future[Option[(Customer, Seq[Option[Phone]])]]
  def getAllWithPhone(): Future[Seq[(Customer, Seq[Option[Phone]])]]

}