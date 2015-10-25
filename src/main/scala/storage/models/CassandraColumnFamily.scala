package storage.models

trait CassandraColumnFamily {
  var columnFamily: String

  def fromJson(json: String): CassandraColumnFamily
  def toJsonString() : String
  def toInsertQuery(): String
  def createTable: String
}
