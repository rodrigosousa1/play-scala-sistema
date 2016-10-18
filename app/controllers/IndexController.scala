package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.i18n.{ MessagesApi, Messages, I18nSupport }
import models.{ Quote, Item, QuoteDetails }
import com.github.nscala_time.time.Imports._

@Singleton
class IndexController @Inject() (val messagesApi: MessagesApi) extends Controller with I18nSupport {

  def index = Action { implicit request =>
    Ok(views.html.index())
  }
}