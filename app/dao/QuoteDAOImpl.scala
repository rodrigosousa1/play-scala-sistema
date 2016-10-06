package dao

import javax.inject._
import models.Quote
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class QuoteDAOImpl @Inject() (dbConfigProvider: DatabaseConfigProvider) extends QuoteDAO with Tables {

  protected val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import driver.api._

  def getById(id: Long): Future[Option[Quote]] = {
    val query = quotesQuery.filter(_.id === id).result.headOption
    db.run(query)
  }

  def getAll(): Future[Seq[Quote]] = {
    db.run(quotesQuery.result)
  }

  def save(quote: Quote): Future[Long] = {
    val query = quotesAutoInc += quote
    db.run(query)
  }

  def delete(id: Long): Future[Int] = {
    val query = quotesQuery.filter(_.id === id).delete
    db.run(query)
  }

  def update(id: Long, quote: Quote): Future[Int] = {
    val query = quotesQuery.filter(_.id === id).update(quote)
    db.run(query)
  }

}