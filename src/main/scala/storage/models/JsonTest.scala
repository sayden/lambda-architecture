import com.websudos.phantom.builder.query.InsertQuery
import com.websudos.phantom.dsl._
import com.websudos.phantom.testkit._
import net.liftweb.json.{DefaultFormats, Extraction, JsonParser, compactRender}

case class JsonTest(prop1: String, prop2: String)

case class JsonClass(
                      id: UUID,
                      name: String,
                      json: JsonTest,
                      jsonList: List[JsonTest]
                      )


class JsonTable extends CassandraTable[JsonTable, JsonClass] {

  implicit val formats = DefaultFormats

  object id extends UUIDColumn(this) with PartitionKey[UUID]
  object name extends StringColumn(this)

  object json extends JsonColumn[JsonTable, JsonClass, JsonTest](this) {
    override def fromJson(obj: String): JsonTest = {
      JsonParser.parse(obj).extract[JsonTest]
    }

    override def toJson(obj: JsonTest): String = {
      compactRender(Extraction.decompose(obj))
    }
  }

  object jsonList extends JsonListColumn[JsonTable, JsonClass, JsonTest](this) {
    override def fromJson(obj: String): JsonTest = {
      JsonParser.parse(obj).extract[JsonTest]
    }

    override def toJson(obj: JsonTest): String = {
      compactRender(Extraction.decompose(obj))
    }
  }

  def fromRow(row: Row): JsonClass = {
    JsonClass(
      id(row),
      name(row),
      json(row),
      jsonList(row)
    )
  }
}

object JsonTableConnector extends JsonTable with PhantomCassandraConnector {
  def store(sample: JsonClass): InsertQuery.Default[JsonTable, JsonClass] = {
    insert
      .value(_.id, sample.id)
      .value(_.name, sample.name)
      .value(_.json, sample.json)
      .value(_.jsonList, sample.jsonList)
  }
}