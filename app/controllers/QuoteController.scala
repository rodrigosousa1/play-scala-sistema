package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import service.QuoteService
import scala.concurrent.Future
import models.{ Quote, Item, QuoteDetails }
import play.api.i18n.{ MessagesApi, Messages, I18nSupport }
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json._
import it.innove.play.pdf.PdfGenerator

@Singleton
class QuoteController @Inject() (qs: QuoteService, val messagesApi: MessagesApi) extends Controller with I18nSupport {

  def generatePdf(id: Long) = Action.async { implicit request =>
    val quotes = qs.getQuoteDetailsById(id)
    quotes.map { quote =>
      quote match {
        case Some(x) => Ok(new PdfGenerator().toBytes(views.html.pdf.pdfTemplate(x), "http://localhost:9000")).as("application/pdf")
        case None => NotFound(Json.obj("status" -> "NOT_FOUND"))
      }
    }

  }

  def index = Action { implicit request =>
    Ok(views.html.quote.index())
  }

  def getAllQuotes() = Action.async { implicit request =>
    val quotes = qs.getAllQuotes
    quotes.map { quotes =>
      Ok(Json.toJson(quotes))
    }
  }

  def getQuoteById(id: Long) = Action.async { implicit request =>
    val quote = qs.getQuoteDetailsById(id)
    quote.map { quote =>
      quote match {
        case Some(x) => Ok(Json.toJson(x))
        case None => NotFound(Json.obj("status" -> "NOT_FOUND"))
      }
    }
  }

  def saveQuote() = Action.async(BodyParsers.parse.json) { implicit request =>
    request.body.validate[QuoteDetails].fold(
      errors => {
        Future.successful(BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toJson(errors))))
      },
      quote => {
        qs.saveQuoteDetails(quote).map { res =>
          Ok(Json.obj("status" -> "OK"))
        }

      })
  }

  def deleteQuote(id: Long) = Action.async { implicit request =>
    val delete = qs.deleteQuote(id)
    delete.map { res =>
      Ok(Json.obj("status" -> "OK"))
    }

  }

}