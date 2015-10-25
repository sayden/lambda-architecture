package consumer

import java.sql.Timestamp
import java.util
import java.util.UUID
import javax.persistence.{EntityManager, EntityManagerFactory, Persistence}

import com.impetus.client.cassandra.common.CassandraConstants
import storage.kundera.{GroupTopic, MeetupKundera}
import storage.{CassandraJavaScalaStorage, CassandraJavaStorage}

object Main {
  def main(args: Array[String]): Unit = {
    tryKundera()
  }

  def tryKundera(): Unit ={
    val puProperties: util.HashMap[String, Object] = new util.HashMap[String, Object]()
    puProperties.put(CassandraConstants.CQL_VERSION, CassandraConstants.CQL_VERSION_3_0)
    val emf: EntityManagerFactory = Persistence.createEntityManagerFactory("cassandra_pu", puProperties)
    val em: EntityManager = emf.createEntityManager()

    //Meetup
    val meetup: MeetupKundera = new MeetupKundera
    meetup.rsvp_id = UUID.randomUUID().toString
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

    em.close
    emf.close
  }

  def tryCassandraJavaScalaStorage(): Unit ={
    val javaScalaCassandra: CassandraJavaScalaStorage = new CassandraJavaScalaStorage()
    javaScalaCassandra.run()

  }

  def tryInheritance(): Unit = {
    val scalaInheritance = new ScalaTutorials
    scalaInheritance.inheritance()
  }

  def tryScalaPromises(): Unit ={
    val scalaPromises: ScalaTutorials = new ScalaTutorials
    scalaPromises.promises
  }

  /**
   * The consumer that listens to "meetup" topic in Kafka for messages
   */
  def tryConsumer(): Unit ={
    val consumer: MeetupConsumer = new MeetupConsumer("localhost:2181", "1", "meetup")
    consumer.run()
  }

  /**
   * A json parsing tests
   */
  def tryJsonParser(): Unit ={
    val test: JsonParserTest = new JsonParserTest
    test.tryParser()
  }

  /**
   * The consumer that listens to "meetup" topic in Kafka.
   * Written in Java
   */
  def tryJavaKafkaConsumer(): Unit ={
    val javaConsumer = new MyJavaKafkaConsumer("localhost:2181", "1", "meetup")
    javaConsumer.testConsumer()
  }

  /**
   * The Java driver to access Cassandra
   */
  def tryCassandraJavaStorage(): Unit ={
    val cassandra: CassandraJavaStorage = new CassandraJavaStorage

    cassandra.connect("localhost")
    cassandra.loadData
    cassandra.close
  }
}
