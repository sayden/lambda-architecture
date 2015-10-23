package storage.models

trait CassandraColumnFamily {
  var columnFamily: String

  def fromJson(json: String): CassandraColumnFamily
  def toJsonString(columnFamily: CassandraColumnFamily) : String
  def toInsertQuery(columnFamily: CassandraColumnFamily): String
}
