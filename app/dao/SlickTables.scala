package dao

import javax.inject._
import models.{ Customer, Phone }
import slick.driver.PostgresDriver

trait Tables {

 import PostgresDriver.api._

  class CustomerTable(tag: Tag) extends Table[Customer](tag, "customer") {
    def id            = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def name          = column[String]("name")
    def cnpj          = column[Long]("cnpj")
    def registration  = column[Long]("registration")

    def *             = (name, cnpj, registration, id) <> (Customer.tupled, Customer.unapply)
  }

  implicit val customersQuery = TableQuery[CustomerTable]
  implicit val customersAutoInc = customersQuery returning customersQuery.map(_.id)

  class PhoneTable(tag: Tag) extends Table[Phone](tag, "phone") {
    def id            = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def customerId    = column[Long]("customer_id")
    def number        = column[Long]("number")

    def *             = (customerId, number, id) <> (Phone.tupled, Phone.unapply)

    def customer = foreignKey("customer_fk", customerId, customersQuery)(_.id, onDelete = ForeignKeyAction.Cascade)

  }

  implicit val phonesQuery = TableQuery[PhoneTable]
  implicit val phonesAutoInc = phonesQuery returning phonesQuery.map(_.id)

}