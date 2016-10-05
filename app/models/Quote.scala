package models

import play.api.libs.json._
import com.github.nscala_time.time.Imports._

case class Quote(serviceTo: String, serviceDescription: String, date: DateTime, id: Long = 0L)

object Quote {

  implicit val quoteFormat = Json.format[Quote]

  def tupled = (this.apply _).tupled

}
