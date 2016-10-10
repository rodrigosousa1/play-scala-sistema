package dao

import javax.inject._
import models._
import com.github.nscala_time.time.Imports._
import slick.driver.PostgresDriver
import java.sql.Timestamp


trait Tables {

  import PostgresDriver.api._

  implicit def dateTime = MappedColumnType.base[DateTime, Timestamp](
      dt => new Timestamp(dt.getMillis),
      ts => new DateTime(ts.getTime))

  class CustomerTable(tag: Tag) extends Table[Customer](tag, "customer") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def cnpj = column[String]("cnpj")
    def registration = column[String]("registration")

    def * = (name, cnpj, registration, id) <> (Customer.tupled, Customer.unapply)
  }

  implicit val customersQuery = TableQuery[CustomerTable]
  implicit val customersAutoInc = customersQuery returning customersQuery.map(_.id)

  class PhoneTable(tag: Tag) extends Table[Phone](tag, "phone") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def customerId = column[Long]("customer_id")
    def number = column[String]("number")

    def * = (customerId, number, id) <> (Phone.tupled, Phone.unapply)
    def customer = foreignKey("customer_fk", customerId, customersQuery)(_.id, onDelete = ForeignKeyAction.Cascade)

  }

  implicit val phonesQuery = TableQuery[PhoneTable]
  implicit val phonesAutoInc = phonesQuery returning phonesQuery.map(_.id)

  class AddressTable(tag: Tag) extends Table[Address](tag, "address") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def customerId = column[Long]("customer_id")
    def street = column[String]("street")
    def neighborhood = column[String]("neighborhood")
    def city = column[String]("city")
    def state = column[String]("state")
    def cep = column[String]("cep")

    def * = (customerId, street, neighborhood, city, state, cep, id) <> (Address.tupled, Address.unapply)
    def customer = foreignKey("customer_fk", customerId, customersQuery)(_.id, onDelete = ForeignKeyAction.Cascade)

  }

  implicit val addressesQuery = TableQuery[AddressTable]
  implicit val addressesAutoInc = addressesQuery returning addressesQuery.map(_.id)

  class QuoteTable(tag: Tag) extends Table[Quote](tag, "quote") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def serviceTo = column[String]("service_to")
    def serviceDescription = column[String]("service_description")
    def date = column[DateTime]("date")
    def total = column[Double]("total")

    def * = (serviceTo, serviceDescription, date, total, id) <> (Quote.tupled, Quote.unapply)

  }

  implicit val quotesQuery = TableQuery[QuoteTable]
  implicit val quotesAutoInc = quotesQuery returning quotesQuery.map(_.id)

  class ItemTable(tag: Tag) extends Table[Item](tag, "item") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def quoteId = column[Long]("quote_id")
    def quantity = column[Int]("quantity")
    def description = column[String]("description")
    def price = column[Double]("price")
    def total = column[Double]("total")

    def * = (quoteId, quantity, description, price, total, id) <> (Item.tupled, Item.unapply)

  }

  implicit val itemsQuery = TableQuery[ItemTable]
  implicit val itemsAutoInc = itemsQuery returning itemsQuery.map(_.id)
}