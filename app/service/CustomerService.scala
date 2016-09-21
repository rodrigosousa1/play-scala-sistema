package service

import com.google.inject.ImplementedBy
import models.{ Customer, Phone }
import scala.concurrent.Future

@ImplementedBy(classOf[CustomerServiceImpl])
trait CustomerService {

  def getCustomerById(id: Long): Future[Option[Customer]]
  def getAllCustomers(): Future[Seq[Customer]]
  def saveCustomer(customer: Customer): Future[Long]
  def deleteCustomer(id: Long): Future[Int]
  def updateCustomer(id: Long, customer: Customer): Future[Int]


  def add(customer: Customer, phones: Seq[Phone]): Future[Option[Int]]
  def update(id: Long, customer: Customer, phones: Seq[Phone]): Future[Int]
  def getCustomerWithPhone(id: Long): Future[Option[(Customer, Seq[Option[Phone]])]]
  def getAllCustomersWithPhone(): Future[Seq[(Customer, Seq[Option[Phone]])]]
}
