package dao

import javax.inject._
import models.Phone
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class PhoneDAOImpl @Inject() (dbConfigProvider: DatabaseConfigProvider) extends PhoneDAO with Tables {

  protected val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import driver.api._

  def getById(id: Long): Future[Option[Phone]] = {
    val query = phonesQuery.filter(_.id === id).result.headOption
    db.run(query)
  }

  def getAll(): Future[Seq[Phone]] = {
    db.run(phonesQuery.result)
  }

  def save(phone: Phone): Future[Long] = {
    val query = phonesAutoInc += phone
    db.run(query)
  }

  def delete(id: Long): Future[Int] = {
    val query = phonesQuery.filter(_.id === id).delete
    db.run(query)
  }

  def update(id: Long, phone: Phone): Future[Int] = {
    val query = phonesQuery.filter(_.id === id).update(phone)
    db.run(query)
  }

}