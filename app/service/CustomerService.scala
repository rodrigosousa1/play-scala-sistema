package service

import com.google.inject.ImplementedBy
import models.CustomerDetails
import scala.concurrent.Future

@ImplementedBy(classOf[CustomerServiceImpl])
trait CustomerService {
  def getCustomerDetailsById(id: Long): Future[Option[CustomerDetails]]
  def getAllCustomersDetails(): Future[Seq[CustomerDetails]]
  def saveCustomerDetails(customerDetails: CustomerDetails): Future[Option[Int]]
  def deleteCustomer(id: Long): Future[Int]
  def updateCustomerDetails(id: Long, customerDetails: CustomerDetails): Future[Int]

}
