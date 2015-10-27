import java.sql.Timestamp
import java.util
import java.util.UUID
import javax.persistence.{EntityManager, EntityManagerFactory, Persistence}

import com.google.gson.Gson
import com.impetus.client.cassandra.common.CassandraConstants
import org.scalatest.{FlatSpec, Matchers}
import storage.kundera.{Meetup, MeetupKundera, GroupTopic}

class MeetupTest extends FlatSpec with Matchers {
  val jsonString = """{
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
}"""

  implicit val formats = net.liftweb.json.DefaultFormats

    behavior of "A Meetup object"

    ignore should "be inserted into cassandra" in {
      val puProperties: util.HashMap[String, Object] = new util.HashMap[String, Object]()
      puProperties.put(CassandraConstants.CQL_VERSION, CassandraConstants.CQL_VERSION_3_0)
      val emf: EntityManagerFactory = Persistence.createEntityManagerFactory("cassandra_pu", puProperties)
      val em: EntityManager = emf.createEntityManager()

      //Meetup
      val meetup: MeetupKundera = new MeetupKundera
      var id = UUID.randomUUID().toString
      meetup.rsvp_id = id
      meetup.guests = 3
      meetup.visibility = "visible"
      meetup.response = "yes"
      meetup.mtime = 1445203710232l

      //Venue
      meetup.venue_id = UUID.randomUUID().toString
      meetup.venue_lat = 32.963917
      meetup.venue_lon = -96.738754
      meetup.venue_name = "My Heart Reiki"

      //Member
      meetup.member_id = 194295521
      meetup.member_name = "Cristin"

      //Event
      meetup.event_name ="REIKI I PRACTITIONER CERTIFICATION CLASS - ONE DAY INTENSIVE"
      meetup.event_id = UUID.randomUUID().toString
      meetup.event_time = new Timestamp(1445783400000l)
      meetup.event_url = "http://www.meetup.com/Holistic-Energy-Healing-Support/events/225901102/"

      meetup.group_city = "Leganes"
      meetup.group_country = "Spain"
      meetup.group_id = 11106032
      meetup.group_lat = 32.96
      meetup.group_lon = -96.75
      meetup.group_name = "SDFasfdasfd"
      meetup.group_urlname = "An url"
      meetup.group_state = "State"
      val gt: GroupTopic = new GroupTopic("An url key1","A topic name1")
      meetup.group_topics.add(gt.toString)

      gt.topic_name = "A topic name2"
      gt.urlkey = "An url key 2"
      meetup.group_topics.add(gt.toString)

      em.persist(meetup)

      //Now check the insertion
      val m: MeetupKundera = em.find(classOf[MeetupKundera], id)

      assert(m.group_city == "Leganes")

      em.close
      emf.close
    }

    behavior of "a json string"

    it should "be converted into a POJO Meetup object" in {
      val gson: Gson = new Gson
      val json = gson.fromJson(jsonString, classOf[Meetup])
      assert(json.group.group_topics.get(0).urlkey == "alternative-health-mind-body-connection")
    }

    it should "be converted into a MeetupKundera object" in {
      val gson: Gson = new Gson
      val json:Meetup = gson.fromJson(jsonString, classOf[Meetup])
      val meetupKundera: MeetupKundera = new MeetupKundera
      meetupKundera.flatten(json)

      assert(meetupKundera.group_topics.get(0) == """{
        "urlkey":"alternative-health-mind-body-connection",
        "topic_name":"Alternative Health • Mind Body Connection"
      }""")
    }
  }
