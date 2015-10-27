package KafkaConsumer

import consumer.{JsonParserTest, MeetupConsumer, MyJavaKafkaConsumer, ScalaTutorials}
import storage.{CassandraJavaScalaStorage, CassandraJavaStorage}

object Main {
  def main(args: Array[String]): Unit = {
    tryConsumer()
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
    val consumer: MeetupConsumer = new MeetupConsumer("meetup")
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
