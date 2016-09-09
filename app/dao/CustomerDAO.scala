package dao

import com.google.inject.ImplementedBy
import models.Customer
import scala.concurrent.Future

@ImplementedBy(classOf[CustomerDAOImpl])
trait CustomerDAO {

  def get(id: Long): Future[Option[Customer]]
  def listAll(): Future[Seq[Customer]]
  def add(customer: Customer): Future[String]
  def delete(id: Long): Future[Int]
  def update(id: Long, customer: Customer): Future[Int]

}