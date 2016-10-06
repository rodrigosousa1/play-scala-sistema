package dao

import com.google.inject.ImplementedBy
import models.{ Quote, QuoteDetails }
import scala.concurrent.Future

@ImplementedBy(classOf[QuoteDAOImpl])
trait QuoteDAO {
  def getById(id: Long): Future[Option[Quote]]
  def getAll(): Future[Seq[Quote]]
  def save(quote: Quote): Future[Long]
  def delete(id: Long): Future[Int]
  def update(id: Long, quote: Quote): Future[Int]

  def getDetailsById(id: Long): Future[Option[QuoteDetails]]
  def getAllDetails(): Future[Seq[QuoteDetails]]
  def saveDetails(quoteDetails: QuoteDetails): Future[Option[Int]]
  def updateDetails(id: Long, quoteDetails: QuoteDetails): Future[Int]
}