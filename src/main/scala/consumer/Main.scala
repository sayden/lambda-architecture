package consumer

import java.util
import java.util.UUID
import javax.persistence.{EntityManager, EntityManagerFactory, Persistence}

import com.impetus.client.cassandra.common.CassandraConstants
import storage.kundera.{MeetupKundera, UserKundera}
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

    //User
    val user: UserKundera = new UserKundera
    user.setName("Mario")
    user.setSurname("Caster")
    user.setAge(30)
    user.setId(UUID.randomUUID())
    em.persist(user)

    //Meetup
    val meetup: MeetupKundera = new MeetupKundera
    meetup.setId(UUID.randomUUID().toString)
    meetup.setGuest(3)
    meetup.setVisibility("visible")

    //Venue
    meetup.setVenueId(UUID.randomUUID().toString)
    meetup.setVenueLat("32.963917")
    meetup.setVenueLon("-96.738754")
    meetup.setVenueName("My Heart Reiki")

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
