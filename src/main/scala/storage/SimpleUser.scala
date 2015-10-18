package storage

import java.util.UUID

import com.datastax.driver.core.{ResultSet, Row}
import com.twitter.conversions.time._
import com.websudos.phantom.dsl._
import com.websudos.phantom.iteratee.Iteratee

import scala.concurrent.Future

class SimpleUser {
  // You can seal the class and only allow importing the companion object.
  // The companion object is where you would implement your custom methods.
  // Keep reading for examples.
  sealed class Users extends CassandraTable[Users, User] {
    object user_id extends  UUIDColumn(this) with PartitionKey[UUID] {
      // You can override the name of your key to whatever you like.
      // The default will be the name used for the object, in this case "id".
      override lazy val name = "user_id"
    }

    // Now we define a column for each field in our case class.
    object fname extends StringColumn(this)
    object lname extends StringColumn(this)

    // Now the mapping function, transforming a row into a custom type.
    // This is a bit of boilerplate, but it's one time only and very short.
    def fromRow(row: Row): User = {
      User(
        user_id(row),
        fname(row),
        lname(row)
      )
    }
  }


  object Users extends Users with ExampleConnector {
    // you can even rename the table in the schema to whatever you like.
    override lazy val tableName = "users"

    // Inserting has a bit of boilerplate on its on.
    // But it's almost always a once per table thing, hopefully bearable.
    // Whatever values you leave out will be inserted as nulls into Cassandra.
    def insertNewRecord(user: User): Future[ResultSet] = {
      insert.value(_.user_id, user.user_id)
        .value(_.fname, user.fname)
        .value(_.lname, user.lname)
        .ttl(150.minutes.inSeconds) // you can use TTL if you want to.
        .future()
    }

    // now you have the full power of Cassandra in really cool one liners.
    // The future will do all the heavy lifting for you.
    // If there is an error you get a failed Future.
    // If there is no matching record you get a None.
    // The "one" method will select a single record, as it's name says.
    // It will always have a LIMIT 1 in the query sent to Cassandra.
    // select.where(_.id eqs UUID.randomUUID()).one() translates to
    // SELECT * FROM my_custom_table WHERE id = the_id_value LIMIT 1;
    def getUserById(id: UUID): Future[Option[User]] = {
      select.where(_.user_id eqs id).one()
    }

    // You can retrieve a whole table, even with billions of records, in a single query.
    // Phantom will collect them into an asynchronous, lazy iterator with very low memory foot print.
    // Enumerators, iterators and iteratees are based on Play iteratees.
    // You can keep the async behaviour or collect through the Iteratee.
    def getEntireTable: Future[Seq[User]] = {
      select.fetchEnumerator() run Iteratee.collect()
    }

    /**
     * Updating records is also really easy. Updating one record is done like this
     * @param id
     * @param name
     * @return
     */
    def updateUserName(id: UUID, name: String): Future[ResultSet] = {
      update.where(_.user_id eqs id).modify(_.fname setTo name).future()
    }

    /**
     * Deleting records has the same restrictions and selecting. If something is non primary, you cannot have it in a where clause.
     * @param id
     * @return
     */
    def deleteUserById(id: UUID): Future[ResultSet] = {
      delete.where(_.user_id eqs id).future()
    }
  }

}
