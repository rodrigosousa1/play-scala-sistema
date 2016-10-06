package dao

import com.google.inject.ImplementedBy
import models.Item
import scala.concurrent.Future

@ImplementedBy(classOf[ItemDAOImpl])
trait ItemDAO {
  def getById(id: Long): Future[Option[Item]]
  def getAll(): Future[Seq[Item]]
  def save(item: Item): Future[Long]
  def delete(id: Long): Future[Int]
  def update(id: Long, item: Item): Future[Int]
}