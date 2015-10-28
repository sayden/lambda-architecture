package storage.kundera

import java.sql.Timestamp
import java.util
import javax.persistence._

import main.Constants

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

  /**
   * Takes a Meetup storage.Meetup POJO and converts it (mainly flatten it) into
   * a more suitable format to store in Cassandra
   * @param meetup
   */
  def convertPojoToCassandraKundera(meetup: Meetup): Unit = {
    if (meetup.rsvp_id != null) rsvp_id = meetup.rsvp_id
    if (meetup.guests != 0) guests = meetup.guests
    if (meetup.visibility != null) visibility = meetup.visibility
    if (meetup.response != null) response = meetup.response
    if (meetup.mtime != 0) mtime = meetup.mtime
    if(meetup.venue != null){
      if (meetup.venue.venue_id != null) venue_id = meetup.venue.venue_id
      if (meetup.venue.lat != 0) venue_lat = meetup.venue.lat
      if(meetup.venue.name != null) venue_name = meetup.venue.name
    }
    if(meetup.member != null){
      if(meetup.member.member_id != 0) member_id = meetup.member.member_id
      if(meetup.member.member_name != null) member_name = meetup.member.member_name
    }

    if(meetup.event != null){
      if(meetup.event.event_id != null) event_id = meetup.event.event_id
      if(meetup.event.event_name != null) event_name = meetup.event.event_name
      if(meetup.event.time != 0) event_time = new Timestamp(meetup.event.time)
      if(meetup.event.event_url != null) event_url = meetup.event.event_url
    }

    if(meetup.group != null){
      if(meetup.group.group_city != null) group_city = meetup.group.group_city
      if(meetup.group.group_country != null) group_country = meetup.group.group_country
      if(meetup.group.group_id != 0) group_id = meetup.group.group_id
      if(meetup.group.group_lat != 0) group_lat = meetup.group.group_lat
      if(meetup.group.group_lon != 0) group_lon = meetup.group.group_lon
      if(meetup.group.group_name != null) group_name = meetup.group.group_name
      if(meetup.group.group_state != null) group_state = meetup.group.group_state
      if(meetup.group.group_urlname != null) group_urlname = meetup.group.group_urlname

      val groupTopicList = meetup.group.group_topics
      val iter = groupTopicList.iterator
      while(iter.hasNext){
        group_topics.add(iter.next().toString)
      }
    }
  }
}
