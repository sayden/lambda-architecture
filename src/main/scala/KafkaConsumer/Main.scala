package KafkaConsumer

import consumer.{MeetupConsumer}

object Main {
  def main(args: Array[String]): Unit = {
    val consumer: MeetupConsumer = new MeetupConsumer("meetup")
    consumer.run()
  }
}
