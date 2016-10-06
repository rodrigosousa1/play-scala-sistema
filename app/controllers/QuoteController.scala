package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.i18n.{ MessagesApi, Messages, I18nSupport }

@Singleton
class QuoteController @Inject() (val messagesApi: MessagesApi) extends Controller with I18nSupport {

  def index = Action { implicit request =>
    Ok(views.html.index())
  }
}