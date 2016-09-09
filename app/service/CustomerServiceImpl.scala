package service

import javax.inject._
import dao.CustomerDAO
import models.Customer
import scala.concurrent.Future

@Singleton
class CustomerServiceImpl @Inject() (customerDAO: CustomerDAO) extends CustomerService {

  def getCustomer(id: Long): Future[Option[Customer]] = {
    customerDAO.get(id)
  }

  def listAllCustomers(): Future[Seq[Customer]] = {
    customerDAO.listAll
  }

  def addCustomer(customer: Customer): Future[String] = {
    customerDAO.add(customer)
  }

  def deleteCustomer(id: Long): Future[Int] = {
    customerDAO.delete(id)
  }

  def updateCustomer(id: Long, customer: Customer): Future[Int] = {
    customerDAO.update(id, customer)
  }
}
