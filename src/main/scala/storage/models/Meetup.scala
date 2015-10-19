package storage.models

case class Meetup(visibility:String, response:String, guests:Int, rsvp_id:Long,
             mtime:Long, venue:Venue, member:Member, event:Event, group:Group) {
}
case class Venue(venue_name:String, lon:Long, lat:Long, venue_id:Long)
case class Member(member_id:Long, member_name:Long)
case class Event(event_name:String, event_id:Long, time:Long,
                 event_url:String)
case class GroupTopic(url_key:String, topic_name:String)
case class Group(groupTopics: List[GroupTopic], group_city:String,
                 group_country:String, group_id:Long, group_name:String,
                 group_lon:Long, group_urlname:String, group_state:String,
                 group_lat:Long)
