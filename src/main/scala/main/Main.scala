package main

import consumer.{MeetupConsumer, MeetupCassandraConsumer, MeetupSparkConsumer}

object Main {
  def main(args: Array[String]): Unit = {
    val cassandraConsumer: MeetupConsumer = new
        MeetupCassandraConsumer("meetup")   //Topic name
    cassandraConsumer.run()

    val sparkConsumer: MeetupSparkConsumer = new MeetupSparkConsumer("meetup")
    sparkConsumer.run
  }
}
