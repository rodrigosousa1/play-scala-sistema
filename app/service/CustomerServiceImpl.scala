package service

import javax.inject._
import dao.CustomerDAO
import models.CustomerDetails
import scala.concurrent.Future

@Singleton
class CustomerServiceImpl @Inject() (customerDAO: CustomerDAO) extends CustomerService {

  def getCustomerDetailsById(id: Long): Future[Option[CustomerDetails]] = {
    customerDAO.getDetailsById(id)
  }

  def getAllCustomersDetails(): Future[Seq[CustomerDetails]] = {
    customerDAO.getAllDetails
  }

  def saveCustomerDetails(customerDetails: CustomerDetails): Future[Int] = {
  	customerDAO.saveDetails(customerDetails)
  }

  def deleteCustomer(id: Long): Future[Int] = {
  	customerDAO.delete(id)
  }

  def updateCustomerDetails(id: Long, customerDetails: CustomerDetails): Future[Int] = {
    customerDAO.updateDetails(id, customerDetails)
  }

}
