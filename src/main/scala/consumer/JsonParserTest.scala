package consumer

import net.liftweb.json.Extraction._
import net.liftweb.json._


class JsonParserTest {

  implicit val formats = net.liftweb.json.DefaultFormats

  // Brings in default date formats etc.
  def tryParser(): Unit = {

    case class Child(name: String, age: Int,birthdate: Option[java.util.Date])
    case class Address(street: String, city: String)
    case class Person(name: String, address: Address,children: List[Child])

    val json = parse(
      """
         { "name": "joe",
           "address": {
             "street": "Bulevard",
             "city": "Helsinki"
           },
           "children": [
             {
               "name": "Mary",
               "age": 5
               "birthdate": "2004-09-04T18:06:22Z"
             },
             {
               "name": "Mazy",
               "age": 3
             }
           ]
         }
                     """)

    val extraction = json.extract[Person]
    println(extraction.name)

    val localTest: LocalTest = new LocalTest("Mario", 30)
    println(pretty(render(decompose(localTest))))
    println(localTest.toString)
  }
}
