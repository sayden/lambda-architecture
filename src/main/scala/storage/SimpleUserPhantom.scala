package storage

/*
class SimpleUserPhantom {
  // The companion object is where you would implement your custom methods.
  sealed class Users extends CassandraTable[Users, User] {
    object user_id extends  UUIDColumn(this) with PartitionKey[UUID] {}

    // Now we define a column for each field in our case class.
    object fname extends StringColumn(this)
    object lname extends StringColumn(this)

    // Now the mapping function, transforming a row into a custom type.
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

    // Whatever values you leave out will be inserted as nulls into Cassandra.
    def insertNewRecord(user: User): Future[ResultSet] = {
      insert.value(_.user_id, user.user_id)
        .value(_.fname, user.fname)
        .value(_.lname, user.lname)
        .ttl(150.minutes.inSeconds) // you can use TTL if you want to.
        .future()
    }

    // If there is an error you get a failed Future.
    // If there is no matching record you get a None.
    // select.where(_.id eqs UUID.randomUUID()).one() translates to
    // SELECT * FROM my_custom_table WHERE id = the_id_value LIMIT 1;
    def getUserById(id: UUID): Future[Option[User]] = {
      select.where(_.user_id eqs id).one()
    }

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
*/