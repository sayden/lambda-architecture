package storage.models

import net.liftweb.json.JsonParser

class Meetup(
              rsvp_id:String,
              visibility:String,
              response:String,
              guests:Int,
              mtime:Long,
              venue:Venue,
              member:Member,
              event:Event,
              group:Group
              ) extends CassandraColumnFamily {

  implicit val formats = net.liftweb.json.DefaultFormats
  override var columnFamily: String = "meetup"

  override def createTable: String = {
    s"CREATE TABLE IF NOT EXISTS $columnFamily (" +
      s"rsvp_id int," +
      s"visibility varchar," +
      s"response varchar," +
      s"guests int," +
      s"mtime int," +
      s"venue_name varchar," +
      s"venue_lon double," +
      s"venue_lat double," +
      s"venue_venue_id int," +
      s"member_id int," +
      s"member_name varchar," +
      s"event_name varchar," +
      s"event_time varchar," +
      s"event_id int," +
      s"event_url varchar," +
      s"group_topics list," +
      s"group_city varchar," +
      s"group_country varchar," +
      s"group_id int," +
      s"group_name varchar," +
      s"group_lon double," +
      s"group_lat double," +
      s"group_urlname varchar," +
      s"group_state varchar," +
      s"PRIMARY KEY (event_name)"
  }

  override def toJsonString(): String = {
    "Mario"
  }

  override def fromJson(json: String): CassandraColumnFamily = {
    JsonParser.parse(json).extract[Meetup]
  }

  override def toInsertQuery(): String = {
    s"INSERT INTO $columnFamily (rsvp_id, venue_name, venue_lon, venue_lat," +
      s"venue_id, visibility, response, guests, member_id, member_name, mtime," +
      s"event_name, event_time, event_id, event_url, group_topics, group_city, " +
      s"group_country, group_id, group_name, group_lon, group_lat, group_urlname," +
      s"group_state) VALUES ($rsvp_id, ${venue.name}, ${venue.lon}, ${venue.lat}," +
      s"${venue.venue_id},$visibility, $response, $guests, ${member.member_id}," +
      s"${member.member_name}, $mtime, ${event.event_name},${event.time}," +
      s"${event.event_id}, ${event.event_url},${group.group_topics}, ${group.group_city}," +
      s"${group.group_country},${group.group_id}, ${group.group_name}, ${group.group_lon}," +
      s"${group.group_lat}, ${group.group_urlname}, ${group.group_state})"
  }

  def groupTopicsToString(groupTopics: List[GroupTopic]): String = {
    "[" + groupTopics.map {topic: GroupTopic =>
      topic.toString
    }.reduceLeft(_ + ", " + _) + "]"
  }
}


case class Venue(name:String, lon:Long, lat:Long, venue_id:Long)

case class Member(member_id:Long, member_name:String)

case class Event(event_name:String, event_id:String, time:String, event_url:String)

case class GroupTopic(url_key: String, event_url:String){
  override def toString(): String = {
    "{event_url:%s, url_key:%s}".format(event_url, url_key)
  }
}

case class GroupTopicList(groupTopicList: List[GroupTopic]){
  override def toString(): String = {
    "[" + groupTopicList.map {topic: GroupTopic =>
      topic.toString
    }.reduceLeft(_ + ", " + _) + "]"
  }
}

case class Group(group_city:String, group_country:String, group_id:Long,
                 group_name:String, group_lon:Long, group_urlname:String,
                 group_state:String,group_lat:Long, group_topics: GroupTopicList)

