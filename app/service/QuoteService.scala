package service

import com.google.inject.ImplementedBy
import models.{ QuoteDetails, Quote }
import scala.concurrent.Future

@ImplementedBy(classOf[QuoteServiceImpl])
trait QuoteService {
  def getAllQuotes(): Future[Seq[Quote]]
  def getQuoteDetailsById(id: Long): Future[Option[QuoteDetails]]
  def getAllQuotesDetails(): Future[Seq[QuoteDetails]]
  def saveQuoteDetails(quoteDetails: QuoteDetails): Future[Option[Int]]
  def deleteQuote(id: Long): Future[Int]
  def updateQuoteDetails(id: Long, quoteDetails: QuoteDetails): Future[Int]

}