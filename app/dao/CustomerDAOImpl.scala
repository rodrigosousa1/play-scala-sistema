package dao

import javax.inject._
import models.{ Customer, Phone }
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import scala.concurrent.Future
import scala.concurrent._

@Singleton
class CustomerDAOImpl @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) extends CustomerDAO {

  protected val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import driver.api._

  class CustomerTable(tag: Tag) extends Table[Customer](tag, "customer") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def cnpj = column[Long]("cnpj")
    def registration = column[Long]("registration")

    def * = (name, cnpj, registration, id) <>
      (Customer.tupled, Customer.unapply)
  }

  class PhoneTable(tag: Tag) extends Table[Phone](tag, "phone") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def customerId = column[Long]("customer_id")
    def number = column[Long]("number")

    def * = (customerId, number, id) <>
      (Phone.tupled, Phone.unapply)

    def customer = foreignKey("customer_fk", customerId, customersQuery)(_.id, onDelete = ForeignKeyAction.Cascade)

  }

  implicit val customersQuery = TableQuery[CustomerTable]
  implicit val customersAutoInc = customersQuery returning customersQuery.map(_.id)
  implicit val phonesQuery = TableQuery[PhoneTable]

  def get(id: Long): Future[Option[(Customer, Seq[Option[Phone]])]] = {
    val getQuery = customersQuery.joinLeft(phonesQuery).on(_.id === _.customerId).
      filter { case (customer, phone) => customer.id === id }.result.map {
        _.groupBy(_._1).map {
          case (c, p) => (c, p.map(_._2))
        }.to[Seq].headOption
      }
    db.run(getQuery)
  }

  def listAll(): Future[Seq[(Customer, Seq[Option[Phone]])]] = {
    val listAllQuery = customersQuery.joinLeft(phonesQuery).on(_.id === _.customerId).result.map {
      _.groupBy(_._1).map {
        case (c, p) => (c, p.map(_._2))
      }.to[Seq]
    }

    db.run(listAllQuery)
  }

  def add(customer: Customer): Future[Long] = {
    val insertQuery = customersAutoInc += customer
    db.run(insertQuery)
  }

  def delete(id: Long): Future[Int] = {
    val deleteQuery = customersQuery.filter(_.id === id).delete
    db.run(deleteQuery)
  }

  def update(id: Long, customer: Customer): Future[Int] = {
    val updateQuery = customersQuery.filter(_.id === id).update(customer)
    db.run(updateQuery)
  }

  def add(phone: List[Phone]): Future[Option[Int]] = {
    val insertQuery = phonesQuery ++= phone
    db.run(insertQuery)
  }

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

}