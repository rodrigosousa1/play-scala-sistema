package dao

import javax.inject._
import models.Customer
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import scala.concurrent.Future
import scala.concurrent._

@Singleton
class CustomerDAOImpl @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) extends CustomerDAO {

  protected val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import driver.api._

  class CustomerTable(tag: Tag) extends Table[Customer](tag, "cliente") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def razaoSocial = column[String]("razao_social")
    def cnpj = column[Long]("cnpj")
    def inscricaoMunicipal = column[Long]("inscricao_municipal")

    def * = (razaoSocial, cnpj, inscricaoMunicipal, id) <>
      (Customer.tupled, Customer.unapply)
  }

  implicit val customers = TableQuery[CustomerTable]

  def get(id: Long): Future[Option[Customer]] = {
    val getQuery = customers.filter(_.id === id).result.headOption
    db.run(getQuery)
  }

  def listAll(): Future[Seq[Customer]] = {
    val listAllQuery = customers.result
    db.run(listAllQuery)
  }

  def add(customer: Customer): Future[String] = {
    val insertQuery = customers += customer
    db.run(insertQuery).map(res => "Successful").recover {
      case ex: Exception => ex.getCause.getMessage
    }
  }

  def delete(id: Long): Future[Int] = {
    val deleteQuery = customers.filter(_.id === id).delete
    db.run(deleteQuery)
  }

  def update(id: Long, customer: Customer): Future[Int] = {
    val updateQuery = customers.filter(_.id === id).update(customer)
    db.run(updateQuery)
  }

}