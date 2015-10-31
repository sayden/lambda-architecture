package main

import consumer.MeetupSparkConsumer

object Main {
  def main(args: Array[String]): Unit = {
//    val cassandraConsumer: MeetupConsumer = new
//        MeetupCassandraConsumer("meetup")   //Topic name

    val sparkConsumer: MeetupSparkConsumer = new MeetupSparkConsumer("meetup")
    sparkConsumer.run
  }
}
