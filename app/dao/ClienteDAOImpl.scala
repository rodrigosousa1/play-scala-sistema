package dao

import javax.inject._
import models.Cliente
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import scala.concurrent.Future
import scala.concurrent._

@Singleton
class ClienteDAOImpl @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) extends ClienteDAO {

  protected val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import driver.api._

  class ClienteTable(tag: Tag) extends Table[Cliente](tag, "cliente") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def razaoSocial = column[String]("razao_social")
    def cnpj = column[Long]("cnpj")
    def inscricaoMunicipal = column[Long]("inscricao_municipal")

    def * = (id, razaoSocial, cnpj, inscricaoMunicipal) <>
      (Cliente.tupled, Cliente.unapply)
  }

  implicit val clientes = TableQuery[ClienteTable]

  def get(id: Long): Future[Option[Cliente]] = {
    val getQuery = clientes.filter(_.id === id).result.headOption
    db.run(getQuery)
  }

  def listAll(): Future[Seq[Cliente]] = {
    val listAllQuery = clientes.result
    db.run(listAllQuery)
  }

  def add(cliente: Cliente): Future[String] = {
    val insertQuery = clientes += cliente
    db.run(insertQuery).map(res => "Successful").recover {
      case ex: Exception => ex.getCause.getMessage
    }
  }

  def delete(id: Long): Future[Int] = {
    val deleteQuery = clientes.filter(_.id === id).delete
    db.run(deleteQuery)
  }

  def update(id: Long, cliente: Cliente): Future[Int] = {
    val updateQuery = clientes.filter(_.id === id).update(cliente)
    db.run(updateQuery)
  }

}