package main

import consumer.{MeetupConsumer}

object Main {
  def main(args: Array[String]): Unit = {
    val consumer: MeetupConsumer = new MeetupConsumer("meetup")   //Topic name
    consumer.run()
  }
}
