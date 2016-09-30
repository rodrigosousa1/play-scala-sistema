package dao

import com.google.inject.ImplementedBy
import models.{ Customer, Phone, CustomerDetails }
import scala.concurrent.Future

@ImplementedBy(classOf[CustomerDAOImpl])
trait CustomerDAO {
  def getById(id: Long): Future[Option[Customer]]
  def getAll(): Future[Seq[Customer]]
  def save(customer: Customer): Future[Long]
  def delete(id: Long): Future[Int]
  def update(id: Long, customer: Customer): Future[Int]

  def getDetailsById(id: Long): Future[Option[CustomerDetails]]
  def getAllDetails(): Future[Seq[CustomerDetails]]
  def saveDetails(customerDetails: CustomerDetails): Future[Option[Int]]
  def updateDetails(id: Long, customerDetails: CustomerDetails): Future[Int]
}