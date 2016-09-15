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

    def customer = foreignKey("customer_fk", customerId, customers)(_.id, onDelete = ForeignKeyAction.Cascade)

  }

  implicit val customers = TableQuery[CustomerTable]
  implicit val customersAutoInc = customers returning customers.map(_.id)
  implicit val phones = TableQuery[PhoneTable]

  def get(id: Long): Future[Option[(Customer, Seq[Option[Phone]])]] = {
    val getQuery = customers.joinLeft(phones).on(_.id === _.customerId).
      filter { case (customer, phone) => customer.id === id }.result.map {
        _.groupBy(_._1).map {
          case (c, p) => (c, p.map(_._2))
        }.to[Seq].headOption
      }
    db.run(getQuery)
  }
  def listAll(): Future[Seq[(Customer, Seq[Option[Phone]])]] = {
    val listAllQuery = customers.joinLeft(phones).on(_.id === _.customerId).result.map {
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
    val deleteQuery = customers.filter(_.id === id).delete
    db.run(deleteQuery)
  }

  def update(id: Long, customer: Customer): Future[Int] = {
    val updateQuery = customers.filter(_.id === id).update(customer)
    db.run(updateQuery)
  }

  def add(phone: List[Phone]): Future[Option[Int]] = {
    val insertQuery = phones ++= phone
    db.run(insertQuery)
  }


}