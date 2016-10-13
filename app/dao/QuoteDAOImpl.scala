package dao

import javax.inject._
import models.{ Quote, QuoteDetails }
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

  def getDetailsById(id: Long): Future[Option[QuoteDetails]] = {
    val query = quotesQuery.joinLeft(itemsQuery).on(_.id === _.quoteId).
      filter { case (quote, item) => quote.id === id }.result.map {
        _.groupBy(_._1).map {
          case (q, i) => QuoteDetails(q.id, q.serviceTo, q.serviceDescription, q.date, i.flatMap(_._2), q.total)
        }.headOption
      }
    db.run(query)
  }

  def getAllDetails(): Future[Seq[QuoteDetails]] = {
    val query = quotesQuery.joinLeft(itemsQuery).on(_.id === _.quoteId).result.map {
      _.groupBy(_._1).map {
        case (q, i) => QuoteDetails(q.id, q.serviceTo, q.serviceDescription, q.date, i.flatMap(_._2),  q.total)
      }.to[Seq]
    }
    db.run(query)
  }

  def saveDetails(quoteDetails: QuoteDetails): Future[Option[Int]] = {
    val quote = Quote(quoteDetails.serviceTo, quoteDetails.serviceDescription, quoteDetails.date, quoteDetails.total, quoteDetails.id)
    val items = quoteDetails.items
    val insertQuote = quotesAutoInc += quote

    def insertDetails(id: Long) = {
      val itemsList = items.map(p => p.copy(quoteId = id)).to[List]
      itemsQuery ++= itemsList
    }

    val query = insertQuote.flatMap { id => insertDetails(id) }
    db.run(query.transactionally)

  }

  def updateDetails(id: Long, quoteDetails: QuoteDetails): Future[Int] = {
    val quote = Quote(quoteDetails.serviceTo, quoteDetails.serviceDescription, quoteDetails.date, quoteDetails.id)
    val items = quoteDetails.items

    val updateQuote = quotesQuery.filter(_.id === id).update(quote)
    val updateItems = DBIO.sequence(items.map(item => itemsQuery.insertOrUpdate(item)))

    val query =  updateItems andThen updateQuote

    db.run(query.transactionally)
  }

}