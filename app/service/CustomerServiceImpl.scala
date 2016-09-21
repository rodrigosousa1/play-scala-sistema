package service

import javax.inject._
import dao.CustomerDAO
import models.{ Customer, Phone }
import scala.concurrent.Future

@Singleton
class CustomerServiceImpl @Inject() (customerDAO: CustomerDAO) extends CustomerService {

  def getCustomerById(id: Long): Future[Option[Customer]] = {
    customerDAO.getById(id)
  }

  def getAllCustomers(): Future[Seq[Customer]] = {
    customerDAO.getAll
  }

  def saveCustomer(customer: Customer): Future[Long] = {
    customerDAO.save(customer)
  }

  def deleteCustomer(id: Long): Future[Int] = {
    customerDAO.delete(id)
  }

  def updateCustomer(id: Long, customer: Customer): Future[Int] = {
    customerDAO.update(id, customer)
  }



  def add(customer: Customer, phones: Seq[Phone]): Future[Option[Int]] = {
    customerDAO.addCustomerWithPhone(customer, phones)
  }

  def update(id: Long, customer: Customer, phones: Seq[Phone]): Future[Int] = {
    customerDAO.updateCustomerWithPhone(id, customer, phones)
  }

   def getCustomerWithPhone(id: Long): Future[Option[(Customer, Seq[Option[Phone]])]] = {
    customerDAO.getWithPhone(id)
  }

  def getAllCustomersWithPhone(): Future[Seq[(Customer, Seq[Option[Phone]])]] = {
    customerDAO.getAllWithPhone
  }

}
