import java.util

case class JsonTest(
                     venue: VenueTest,
                     visibility: String,
                     guests: Int,
                     group: GroupTest,
                     rsvp_id: String
                     ) {
}
case class VenueTest(venue_name: String, lat:Double)
case class GroupTest(group_topics: util.ArrayList[GroupTopicTest], group_lat:Double)
case class GroupTopicTest(urlkey: String, topic_name: String)
