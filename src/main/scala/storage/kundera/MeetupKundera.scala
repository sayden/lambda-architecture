package storage.kundera

import java.sql.Timestamp
import java.util
import javax.persistence._

@Entity
@Table(name = "meetup", schema = "kunderaexamples@cassandra_pu")
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
  var venueName: String = null

  @Column(name = "venue_lon")
  var venueLon: Double = 0

  @Column(name = "venue_lat")
  var venueLat: Double = 0

  @Column(name = "venue_id")
  var venueId: String = null

  /* MEMBER */
  @Column (name = "member_id")
  var memberId: Double = 0

  @Column (name = "member_name")
  var memberName: String = null

  /* EVENT */
  @Column (name = "event_name")
  var eventName: String = null

  @Column (name = "event_id")
  var eventId: String = null

  @Column (name = "event_time")
  var eventTime: Timestamp = null

  @Column (name = "event_url")
  var eventUrl: String = null

  /* GROUP */
  @Column (name = "group_city")
  var groupCity: String = null

  @Column (name = "group_country")
  var groupCountry: String = null

  @Column (name = "group_id")
  var groupId: Double = 0

  @Column (name = "group_name")
  var groupName: String = null

  @Column (name = "group_lon")
  var groupLon: Double = 0

  @Column (name = "group_lat")
  var groupLat: Double = 0

  @Column (name = "group_state")
  var groupState: String = null

  @Column (name = "group_urlname")
  var groupUrlName: String = null

  @ElementCollection
  @Column (name = "group_topics")
  var groupTopicList: java.util.List[String] = new util.ArrayList[String]()

}

class GroupTopic{

  var url_key:String = null

  var topic_name:String = null

  override def toString(): String = {
    s"{url_key:$url_key, topic_name:$topic_name}"
  }
}
