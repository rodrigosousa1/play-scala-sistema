package dao

import javax.inject._
import models.Item
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class ItemDAOImpl @Inject() (dbConfigProvider: DatabaseConfigProvider) extends ItemDAO with Tables {

  protected val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import driver.api._

  def getById(id: Long): Future[Option[Item]] = {
    val query = itemsQuery.filter(_.id === id).result.headOption
    db.run(query)
  }

  def getAll(): Future[Seq[Item]] = {
    db.run(itemsQuery.result)
  }

  def save(item: Item): Future[Long] = {
    val query = itemsAutoInc += item
    db.run(query)
  }

  def delete(id: Long): Future[Int] = {
    val query = itemsQuery.filter(_.id === id).delete
    db.run(query)
  }

  def update(id: Long, item: Item): Future[Int] = {
    val query = itemsQuery.filter(_.id === id).update(item)
    db.run(query)
  }

}