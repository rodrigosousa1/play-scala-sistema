package service

import com.google.inject.ImplementedBy
import models.{ Customer, Phone }
import scala.concurrent.Future

@ImplementedBy(classOf[CustomerServiceImpl])
trait CustomerService {

  def getCustomer(id: Long): Future[Option[(Customer, Seq[Option[Phone]])]]
  def listAllCustomers(): Future[Seq[(Customer, Seq[Option[Phone]])]]
  def addCustomer(customer: Customer): Future[Long]
  def deleteCustomer(id: Long): Future[Int]
  def updateCustomer(id: Long, customer: Customer): Future[Int]

  def addPhone(phone: List[Phone]): Future[Option[Int]]
}
