package storage.models

import net.liftweb.json.JsonParser

class Meetup(
              visibility:String,
              response:String,
              guests:Int,
              rsvp_id:String,
              mtime:Long,
              venue:Venue,
              member:Member,
              event:Event,
              group:Group
              ) extends CassandraColumnFamily {

  implicit val formats = net.liftweb.json.DefaultFormats
  override var columnFamily: String = "meetup"

  override def toJsonString(columnFamily: CassandraColumnFamily): String = {
    "Mario"
  }

  override def fromJson(json: String): CassandraColumnFamily = {
    JsonParser.parse(json).extract[Meetup]
  }

  override def toInsertQuery(m: CassandraColumnFamily): String = {
    val groupTopics: String = groupTopicsToString(this.group.group_topics)

    val meetup = m match {
      case m: Meetup => m
      case _ =>   throw new ClassCastException
    }

    val query: String = s"INSERT INTO cf (rsvp_id, venue_name, venue_lon, venue_lat," +
      s"venue_id, visibility, response, guests, member_id, member_name, mtime," +
      s"event_name, event_time, event_id, event_url, group_topics, group_city, " +
      s"group_country, group_id, group_name, group_lon, group_lat, group_urlname," +
      s"group_state) VALUES ($rsvp_id, ${venue.name}, ${venue.lon}, ${venue.lat}," +
      s"${venue.venue_id},$visibility, $response, $guests, ${member.member_id}," +
      s"${member.member_name}, $mtime, ${event.event_name},${event.time}," +
      s"${event.event_id}, ${event.event_url},${groupTopics}, ${group.group_city}," +
      s"${group.group_country},${group.group_id}, ${group.group_name}, ${group.group_lon}," +
      s"${group.group_lat}, ${group.group_urlname}, ${group.group_state})"

    query
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

case class Group(group_city:String, group_country:String, group_id:Long,
                 group_name:String, group_lon:Long, group_urlname:String,
                 group_state:String,group_lat:Long, group_topics: List[GroupTopic])

