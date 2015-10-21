

case class JsonTest(
                     venue: VenueTest,
                     visibility: String,
                     guests: Int,
                     group: GroupTest,
                     rsvp_id: String
                     ) {
}
case class VenueTest(venue_name: String, lat:Long)
case class GroupTest(group_topics: List[GroupTopicTest], group_lat:Long)
case class GroupTopicTest(urlkey: String, topic_name: String)
