import net.liftweb.json._
import org.scalatest.{FlatSpec, Matchers}
import storage.models.Meetup

class MeetupTests extends FlatSpec with Matchers {
  implicit val formats = net.liftweb.json.DefaultFormats

  "A meetup " should " be created from a JSON" in {

    val json = parse("""{
    "venue":{
        "venue_name":"My Heart Reiki ",
        "lon":-96.738754,
        "lat":32.963917,
        "venue_id":23512714
    },
    "visibility":"public",
    "response":"yes",
    "guests":0,
    "member":{
        "member_id":194295521,
        "member_name":"Cristin"
    },
    "rsvp_id":1576211622,
    "mtime":1445203710232,
    "event":{
        "event_name":"REIKI I PRACTITIONER CERTIFICATION CLASS - ONE DAY INTENSIVE",
        "event_id":"225901102",
        "time":1445783400000,
        "event_url":"http://www.meetup.com/Holistic-Energy-Healing-Support/events/225901102/"
    },
    "group":{
        "group_topics":[
            {
                "urlkey":"alternative-health-mind-body-connection",
                "topic_name":"Alternative Health • Mind Body Connection"
            },
            {
                "urlkey":"alternative-holistic-health",
                "topic_name":"Alternative & Holistic Health"
            },
            {
                "urlkey":"positive-thinking",
                "topic_name":"Positive Thinking"
            },
            {
                "urlkey":"energy-healing",
                "topic_name":"Energy Healing"
            },
            {
                "urlkey":"holistic-health",
                "topic_name":"Holistic Health"
            },
            {
                "urlkey":"alternative-medicine",
                "topic_name":"Alternative Medicine"
            },
            {
                "urlkey":"wellness",
                "topic_name":"Wellness"
            },
            {
                "urlkey":"manifestation",
                "topic_name":"Manifestation"
            },
            {
                "urlkey":"achieving-your-goals",
                "topic_name":"Achieving Goals"
            },
            {
                "urlkey":"self-empowerment",
                "topic_name":"Self-Empowerment"
            },
            {
                "urlkey":"personal-development",
                "topic_name":"Personal Development"
            },
            {
                "urlkey":"natural-health",
                "topic_name":"Natural Health"
            },
            {
                "urlkey":"consciousness",
                "topic_name":"Consciousness"
            },
            {
                "urlkey":"reiki",
                "topic_name":"Reiki"
            }
        ],
        "group_city":"Richardson",
        "group_country":"us",
        "group_id":11106032,
        "group_name":"Holistic & Energy Healing Support Group - DFW",
        "group_lon":-96.75,
        "group_urlname":"Holistic-Energy-Healing-Support",
        "group_state":"TX",
        "group_lat":32.96
    }
}""".stripMargin)

    println(json)
    val meetup = json.extract[Meetup]

    meetup.visibility should be ("public")
    meetup.group.group_topics.size should be (2)
  }

  "A JsonTest " should "create a table" in {
    var jsonTable: JsonTable = new JsonTable
    jsonTable.create(new JsonClass())


  }
}
