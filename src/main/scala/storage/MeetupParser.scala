package storage

import net.liftweb.json._
import storage.models.Meetup

class MeetupParser {
  implicit val formats = net.liftweb.json.DefaultFormats

  def parseJson(stringifiedJson: String): Unit ={
    val json = parse(stringifiedJson)
    val extraction = json.extract[Meetup]
  }
}
