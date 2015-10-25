import java.sql.Timestamp
import java.util
import java.util.UUID
import javax.persistence.{EntityManager, EntityManagerFactory, Persistence}

import com.impetus.client.cassandra.common.CassandraConstants
import org.scalatest.{FlatSpec, Matchers}
import storage.kundera.{MeetupKundera, GroupTopic}

class MeetupTest extends FlatSpec with Matchers {
  implicit val formats = net.liftweb.json.DefaultFormats

    behavior of "A Meetup object"

    it should "be inserted into cassandra" in {
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
      meetup.venueId = UUID.randomUUID().toString
      meetup.venueLat = 32.963917
      meetup.venueLon = -96.738754
      meetup.venueName = "My Heart Reiki"

      //Member
      meetup.memberId = 194295521
      meetup.memberName = "Cristin"

      //Event
      meetup.eventName ="REIKI I PRACTITIONER CERTIFICATION CLASS - ONE DAY INTENSIVE"
      meetup.eventId = UUID.randomUUID().toString
      meetup.eventTime = new Timestamp(1445783400000l)
      meetup.eventUrl = "http://www.meetup.com/Holistic-Energy-Healing-Support/events/225901102/"

      meetup.groupCity = "Leganes"
      meetup.groupCountry = "Spain"
      meetup.groupId = 11106032
      meetup.groupLat = 32.96
      meetup.groupLon = -96.75
      meetup.groupName = "SDFasfdasfd"
      meetup.groupUrlName = "An url"
      meetup.groupState = "State"
      val gt = new GroupTopic
      gt.topic_name = "A topic name1"
      gt.url_key = "An url key1"
      meetup.groupTopicList.add(gt.toString)

      gt.topic_name = "A topic name2"
      gt.url_key = "An url key 2"
      meetup.groupTopicList.add(gt.toString)

      em.persist(meetup)

      /** Now check the insertion */

      val m: MeetupKundera = em.find(classOf[MeetupKundera], id)

      assert(m.groupCity == "Leganes")

      em.close
      emf.close
    }
  }
