package storage

import net.liftweb.json._
import storage.kundera.MeetupKundera

object MeetupParser {
  implicit val formats = net.liftweb.json.DefaultFormats

  def parseString(stringifiedJson: String): MeetupKundera ={
    val json = parse(stringifiedJson)
    val extraction = json.extract[MeetupKundera]
    extraction
  }
}
