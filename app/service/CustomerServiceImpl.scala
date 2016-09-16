package service

import javax.inject._
import dao.CustomerDAO
import models.{ Customer, Phone }
import scala.concurrent.Future

@Singleton
class CustomerServiceImpl @Inject() (customerDAO: CustomerDAO) extends CustomerService {

  def getCustomer(id: Long): Future[Option[(Customer, Seq[Option[Phone]])]] = {
    customerDAO.get(id)
  }

  def listAllCustomers(): Future[Seq[(Customer, Seq[Option[Phone]])]] = {
    customerDAO.listAll
  }

  def addCustomer(customer: Customer): Future[Long] = {
    customerDAO.add(customer)
  }

  def deleteCustomer(id: Long): Future[Int] = {
    customerDAO.delete(id)
  }

  def updateCustomer(id: Long, customer: Customer): Future[Int] = {
    customerDAO.update(id, customer)
  }

  def addPhone(phone: List[Phone]): Future[Option[Int]] = {
    customerDAO.add(phone)
  }

  def add(customer: Customer, phones: Seq[Phone]): Future[Option[Int]] = {
    customerDAO.addCustomerWithPhone(customer, phones)
  }

  def update(id: Long, customer: Customer, phones: Seq[Phone]): Future[Int] = {
    customerDAO.updateCustomerWithPhone(id, customer, phones)
  }

}
