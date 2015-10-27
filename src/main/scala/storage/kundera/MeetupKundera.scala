package storage.kundera

import java.sql.Timestamp
import java.util
import javax.persistence._

import KafkaConsumer.Constants

@Entity
@Table(name = Constants.Tables.MEETUP, schema = Constants.SCHEMA_NAME)
class MeetupKundera {

  /* COMMON */
  @Id
  var rsvp_id: String = null

  @Column
  var guests: Int = 0

  @Column
  var visibility: String = null

  @Column
  var response: String = null

  @Column
  var mtime: Long = 0


  /* VENUE */
  @Column(name = "venue_name")
  var venue_name: String = null

  @Column(name = "venue_lon")
  var venue_lon: Double = 0

  @Column(name = "venue_lat")
  var venue_lat: Double = 0

  @Column(name = "venue_id")
  var venue_id: String = null

  /* MEMBER */
  @Column(name = "member_id")
  var member_id: Double = 0

  @Column(name = "member_name")
  var member_name: String = null

  /* EVENT */
  @Column(name = "event_name")
  var event_name: String = null

  @Column(name = "event_id")
  var event_id: String = null

  @Column(name = "event_time")
  var event_time: Timestamp = null

  @Column(name = "event_url")
  var event_url: String = null

  /* GROUP */
  @Column(name = "group_city")
  var group_city: String = null

  @Column(name = "group_country")
  var group_country: String = null

  @Column(name = "group_id")
  var group_id: Double = 0

  @Column(name = "group_name")
  var group_name: String = null

  @Column(name = "group_lon")
  var group_lon: Double = 0

  @Column(name = "group_lat")
  var group_lat: Double = 0

  @Column(name = "group_state")
  var group_state: String = null

  @Column(name = "group_urlname")
  var group_urlname: String = null

  @ElementCollection
  @Column(name = "group_topics")
  var group_topics: java.util.List[String] = new util.ArrayList[String]()

  def flatten(meetup: Meetup): Unit = {
    rsvp_id = meetup.rsvp_id
    guests = meetup.guests
    visibility = meetup.visibility
    response = meetup.response
    mtime = meetup.mtime
    venue_id = meetup.venue.venue_id
    venue_lat = meetup.venue.lat
    venue_lon = meetup.venue.lon
    venue_name = meetup.venue.name
    member_id = meetup.member.member_id
    member_name = meetup.member.member_name
    event_id = meetup.event.event_id
    event_name = meetup.event.event_name
    event_time = new Timestamp(meetup.event.time)
    event_url = meetup.event.event_url
    group_city = meetup.group.group_city
    group_country = meetup.group.group_country
    group_id = meetup.group.group_id
    group_lat = meetup.group.group_lat
    group_lon = meetup.group.group_lon
    group_name = meetup.group.group_name
    group_state = meetup.group.group_state
    group_urlname = meetup.group.group_urlname
    val groupTopicList: java.util.ArrayList[GroupTopic] = meetup.group.group_topics
    for(gt: GroupTopic <- groupTopicList){
      println(gt)
    }
  }
}
