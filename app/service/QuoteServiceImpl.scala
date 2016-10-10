package service

import javax.inject._
import dao.QuoteDAO
import models.{ QuoteDetails, Quote }
import scala.concurrent.Future

@Singleton
class QuoteServiceImpl @Inject() (quoteDAO: QuoteDAO) extends QuoteService {

  def getAllQuotes(): Future[Seq[Quote]] = {
    quoteDAO.getAll;
  }

  def getQuoteDetailsById(id: Long): Future[Option[QuoteDetails]] = {
    quoteDAO.getDetailsById(id)
  }
  def getAllQuotesDetails(): Future[Seq[QuoteDetails]] = {
    quoteDAO.getAllDetails
  }
  def saveQuoteDetails(quoteDetails: QuoteDetails): Future[Option[Int]] = {
    quoteDAO.saveDetails(quoteDetails)
  }
  def deleteQuote(id: Long): Future[Int] = {
    quoteDAO.delete(id)
  }
  def updateQuoteDetails(id: Long, quoteDetails: QuoteDetails): Future[Int] = {
    quoteDAO.updateDetails(id, quoteDetails)
  }

}