package consumer

import java.util.UUID

import storage.{CassandraJavaStorage, SimpleUser, User}

import scala.concurrent.Future
import concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object Main {
  def main(args: Array[String]) {
    tryInheritance()
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


  /**
   * Phantom, one of the Scala drivers to access Cassandra
   */
  def tryPhantom(): Unit = {
    val simpleUser: SimpleUser = new SimpleUser
    simpleUser.Users.insertNewRecord(new User(UUID.randomUUID(), "Mario", "Caster"))

    val userPromise: Future[Seq[User]] = simpleUser.Users.getEntireTable
    userPromise onComplete{
      case Success(users) => {
        users.foreach((user: User) => {
          printf("Name: %s, Surname: %s, (id:%s)\n", user.fname, user.lname, user.user_id)
        })
        println("Finished")
      }
      case Failure(ex) => println("Error", ex)
    }
  }
}
