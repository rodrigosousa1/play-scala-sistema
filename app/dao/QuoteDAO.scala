package dao

import com.google.inject.ImplementedBy
import models.Quote
import scala.concurrent.Future

@ImplementedBy(classOf[QuoteDAOImpl])
trait QuoteDAO {
  def getById(id: Long): Future[Option[Quote]]
  def getAll(): Future[Seq[Quote]]
  def save(quote: Quote): Future[Long]
  def delete(id: Long): Future[Int]
  def update(id: Long, quote: Quote): Future[Int]
}