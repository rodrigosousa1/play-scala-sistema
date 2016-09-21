package dao

import javax.inject._
import models.{ Customer, Phone }
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


case class CuustomerDetails(name: String, cnpj: Long, registration: Long, phones: List[Phone], id: Long = 0L)

@Singleton
class CustomerDAOImpl @Inject() (dbConfigProvider: DatabaseConfigProvider) extends CustomerDAO with Tables {

  protected val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import driver.api._

  def getById(id: Long): Future[Option[Customer]] = {
    val query = customersQuery.filter(_.id === id).result.headOption
    db.run(query)
  }

  def getAll(): Future[Seq[Customer]] = {
    db.run(customersQuery.result)
  }

  def save(customer: Customer): Future[Long] = {
    val query = customersAutoInc += customer
    db.run(query)
  }

  def delete(id: Long): Future[Int] = {
    val query = customersQuery.filter(_.id === id).delete
    db.run(query)
  }

  def update(id: Long, customer: Customer): Future[Int] = {
    val query = customersQuery.filter(_.id === id).update(customer)
    db.run(query)
  }




  /**
   * 
   ******************************************************************************************/

  def addCustomerWithPhone(customer: Customer, phones: Seq[Phone]): Future[Option[Int]] = {

    val insertCustomer = customersAutoInc += customer

    def insertPhone(id: Long) = {
      val phonesList = phones.map(p => Phone(id, p.number)).to[List]
      phonesQuery ++= phonesList
    }

    val insertQuery = insertCustomer.flatMap { id => insertPhone(id) }
    db.run(insertQuery.transactionally)
  }

  def updateCustomerWithPhone(id: Long, customer: Customer, phones: Seq[Phone]): Future[Int] = {
    val updateCustomerQuery = customersQuery.filter(_.id === id).update(customer)

    for (phone <- phones) {
      db.run(phonesQuery.insertOrUpdate(phone))
    }

    db.run { updateCustomerQuery }

  }

  def getWithPhone(id: Long): Future[Option[(Customer, Seq[Option[Phone]])]] = {
    val getQuery = customersQuery.joinLeft(phonesQuery).on(_.id === _.customerId).
      filter { case (customer, phone) => customer.id === id }.result.map {
        _.groupBy(_._1).map {
          case (c, p) => (c, p.map(_._2))
        }.to[Seq].headOption
      }
    db.run(getQuery)
  }

  def getAllWithPhone(): Future[Seq[(Customer, Seq[Option[Phone]])]] = {
    val listAllQuery = customersQuery.joinLeft(phonesQuery).on(_.id === _.customerId).result.map {
      _.groupBy(_._1).map {
        case (c, p) => (c, p.map(_._2))
      }.to[Seq]
    }

    db.run(listAllQuery)
  }
  /**
   * 
   *******************************************************************************************************/

}