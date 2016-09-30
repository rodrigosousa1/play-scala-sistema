package dao

import javax.inject._
import models.{ Customer, Phone, CustomerDetails }
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

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

  def getDetailsById(id: Long): Future[Option[CustomerDetails]] = {
    val query = customersQuery.joinLeft(phonesQuery).on(_.id === _.customerId).
      filter { case (customer, phone) => customer.id === id }.result.map {
        _.groupBy(_._1).map {
          case (c, p) => CustomerDetails(c.id, c.name, c.cnpj, c.registration, p.flatMap(_._2))
        }.to[Seq].headOption
      }
    db.run(query)
  }

  def getAllDetails(): Future[Seq[CustomerDetails]] = {
    val query = customersQuery.joinLeft(phonesQuery).on(_.id === _.customerId).result.map {
      _.groupBy(_._1).map {
        case (c, p) => CustomerDetails(c.id, c.name, c.cnpj, c.registration, p.flatMap(_._2))
      }.to[Seq]
    }

    db.run(query)
  }

  def saveDetails(customerDetails: CustomerDetails): Future[Option[Int]] = {
    val customer = Customer(customerDetails.name, customerDetails.cnpj, customerDetails.registration, customerDetails.id)
    val phones = customerDetails.phones

    val insertCustomer = customersAutoInc += customer

    def insertPhone(id: Long) = {
      val phonesList = phones.map(p => Phone(id, p.number)).to[List]
      phonesQuery ++= phonesList
    }

    val insertQuery = insertCustomer.flatMap { id => insertPhone(id) }
    db.run(insertQuery.transactionally)

  }

  def updateDetails(id: Long, customerDetails: CustomerDetails): Future[Int] = {
    val customer = Customer(customerDetails.name, customerDetails.cnpj, customerDetails.registration, customerDetails.id)
    val phones = customerDetails.phones

    val query = customersQuery.filter(_.id === id).update(customer)

    for (phone <- phones) {
      db.run(phonesQuery.insertOrUpdate(phone))
    }

    db.run { query }

  }
}