package service

import com.google.inject.ImplementedBy
import models.Customer
import scala.concurrent.Future

@ImplementedBy(classOf[CustomerServiceImpl])
trait CustomerService {

  def getCustomer(id: Long): Future[Option[Customer]]
  def listAllCustomers(): Future[Seq[Customer]]
  def addCustomer(customer: Customer): Future[String]
  def deleteCustomer(id: Long): Future[Int]
  def updateCustomer(id: Long, customer: Customer): Future[Int]

}
