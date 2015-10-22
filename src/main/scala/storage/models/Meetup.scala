package storage.models

import com.websudos.phantom.dsl._
import com.websudos.phantom.iteratee.Iteratee
import storage.ExampleConnector
import net.liftweb.json._

import scala.concurrent.Future

case class Meetup(
                   visibility:String,
                   response:String,
                   guests:Int,
                   rsvp_id:String,
                   mtime:Long,
                   venue:Venue,
                   member:Member,
                   event:Event,
                   group:Group
                   ) {
}
case class Venue(venue_name:String, lon:Long, lat:Long, venue_id:Long)
case class Member(member_id:Long, member_name:String)
case class Event(event_name:String, event_id:String, time:String,
                 event_url:String)
case class GroupTopic(url_key: String, event_url:String)
case class Group(group_city:String,
                 group_country:String, group_id:Long, group_name:String,
                 group_lon:Long, group_urlname:String, group_state:String,
                 group_lat:Long, group_topics: List[String])

sealed class Meetups extends CassandraTable[Meetups, Meetup]{
  implicit val formats = DefaultFormats

//  object rsvp_id extends UUIDColumn(this) with PartitionKey[UUID]{}
  object rsvp_id extends StringColumn(this) {}

  //Columns
  object venue_name extends StringColumn(this)
  object venue_lon extends LongColumn(this)
  object venue_lat extends LongColumn(this)
  object venue_id extends LongColumn(this)    //Actually venue_venue_id
  object visibility extends StringColumn(this)
  object response extends StringColumn(this)
  object guests extends IntColumn(this)
  object member_id extends LongColumn(this)
  object member_name extends StringColumn(this)
  object mtime extends LongColumn(this)
  object event_name extends StringColumn(this)
  object event_id extends StringColumn(this)
  object event_time extends StringColumn(this)
  object event_url extends StringColumn(this)
  object group_topics extends JsonListColumn[Meetups, Meetup, GroupTopic](this) {
    override def fromJson(obj: String): GroupTopic ={
      JsonParser.parse(obj).extract[GroupTopic]
    }

    override def toJson(groupTopic: GroupTopic): String = {
      compactRender(Extraction.decompose(groupTopic))
    }
  }
  object group_city extends StringColumn(this)
  object group_country extends StringColumn(this)
  object group_id extends LongColumn(this)
  object group_name extends StringColumn(this)
  object group_lon extends LongColumn(this)
  object group_urlname extends StringColumn(this)
  object group_state extends StringColumn(this)
  object group_lat extends LongColumn(this)

  def fromRow(row: Row): Meetup ={
    Meetup(
      visibility(row),
      response(row),
      guests(row),
      rsvp_id(row),
      mtime(row),
      new Venue(venue_name(row), venue_lon(row), venue_lat(row), venue_id(row)),
      new Member(member_id(row), member_name(row)),
      new Event(event_name(row), event_id(row), event_time(row), event_url(row)),
      new Group(group_city(row), group_country(row),
        group_id(row), group_name(row), group_lon(row), group_urlname(row),
        group_state(row), group_lat(row), List[new GroupTopic(url_key(row), event_url(row)])
    )
  }
}

object Meetups extends Meetups with ExampleConnector {
  override lazy val tableName = "meetup"

  def insertNewRecord(meetup: Meetup): Future[ResultSet] = {
    insert
      .value(_.rsvp_id, meetup.rsvp_id)
      .value(_.visibility, meetup.visibility)
      .value(_.venue_name, meetup.venue.venue_name)
      .value(_.venue_lon, meetup.venue.lon)
      .value(_.venue_lat, meetup.venue.lat)
      .value(_.venue_id, meetup.venue.venue_id)
      .value(_.response, meetup.response)
      .value(_.guests, meetup.guests)
      .value(_.member_id, meetup.member.member_id)
      .value(_.member_name, meetup.member.member_name)
      .value(_.mtime, meetup.mtime)
      .value(_.event_name, meetup.event.event_name)
      .value(_.event_id, meetup.event.event_id)
      .value(_.event_time, meetup.event.time)
      .value(_.event_url, meetup.event.event_url)
      .value(_.group_topics, meetup.group.group_topics)
      .value(_.group_city, meetup.group.group_city)
      .value(_.group_country, meetup.group.group_country)
      .value(_.group_id, meetup.group.group_id)
      .value(_.group_name, meetup.group.group_name)
      .value(_.group_lon, meetup.group.group_lon)
      .value(_.group_lat, meetup.group.group_lat)
      .value(_.group_urlname, meetup.group.group_urlname)
      .value(_.group_state, meetup.group.group_state)
      .future()
  }

  def getEntireTable: Future[Seq[Meetup]] = {
    select.fetchEnumerator() run Iteratee.collect()
  }
}