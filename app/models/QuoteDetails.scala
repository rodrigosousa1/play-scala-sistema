package models

import play.api.libs.json._
import com.github.nscala_time.time.Imports._

case class QuoteDetails(id: Long = 0L, serviceTo: String, serviceDescription: String, date: DateTime, items: Seq[Item], total: Double)

object QuoteDetails {

  implicit val QuoteDetailsFormat = Json.format[QuoteDetails]

}