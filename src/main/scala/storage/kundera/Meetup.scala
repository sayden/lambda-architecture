package storage.kundera

class Meetup(
                var rsvp_id:String,
                var visibility:String,
                var response:String,
                var guests:Int,
                var mtime:Long,
                var venue:Venue,
                var member:Member,
                var event:Event,
                var group:Group
                )

case class Venue(name:String, lon:Double, lat:Double, venue_id:String)

case class Member(member_id:Long, member_name:String)

case class Event(event_name:String, event_id:String, time:Long, event_url:String)

case class GroupTopic(var urlkey: String, var topic_name:String){

  override def toString = s"{url_key:$urlkey, topic_name:$topic_name}"

}

case class Group(group_city:String, group_country:String, group_id:Long,
                 group_name:String, group_lon:Double, group_urlname:String,
                 group_state:String,group_lat:Double,
                 group_topics: java.util.ArrayList[GroupTopic])



