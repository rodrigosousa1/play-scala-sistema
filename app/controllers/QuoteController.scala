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

@Singleton
class QuoteController @Inject() (qs: QuoteService, val messagesApi: MessagesApi) extends Controller with I18nSupport {

  def index = Action { implicit request =>
    Ok(views.html.index())
  }

  def getAllQuotes() = Action.async { implicit request =>
    val quotes = qs.getAllQuotesDetails
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


}