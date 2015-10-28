package KafkaConsumer

import consumer.{MeetupConsumer}

object Main {
  def main(args: Array[String]): Unit = {

  }


  /**
   * The consumer that listens to "meetup" topic in Kafka for messages
   */
  def tryConsumer(): Unit ={
    val consumer: MeetupConsumer = new MeetupConsumer("meetup")
    consumer.run()
  }
}
