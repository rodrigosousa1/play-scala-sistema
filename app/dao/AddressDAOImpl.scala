package dao

import javax.inject._
import models.Address
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class AddressDAOImpl @Inject() (dbConfigProvider: DatabaseConfigProvider) extends AddressDAO with Tables {

  protected val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import driver.api._

  def getById(id: Long): Future[Option[Address]] = {
    val query = addressesQuery.filter(_.id === id).result.headOption
    db.run(query)
  }

  def getAll(): Future[Seq[Address]] = {
    db.run(addressesQuery.result)
  }

  def save(address: Address): Future[Long] = {
    val query = addressesAutoInc += address
    db.run(query)
  }

  def delete(id: Long): Future[Int] = {
    val query = addressesQuery.filter(_.id === id).delete
    db.run(query)
  }

  def update(id: Long, address: Address): Future[Int] = {
    val query = addressesQuery.filter(_.id === id).update(address)
    db.run(query)
  }

}