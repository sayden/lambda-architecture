package main

import consumer.{MeetupCassandraConsumer, MeetupSparkConsumer}

object Main {
  def main(args: Array[String]): Unit = {
    
    val sparkConsumer = new Thread(new Runnable {
      override def run(): Unit = {
        val sparkConsumer: MeetupSparkConsumer = new MeetupSparkConsumer("meetup")
        sparkConsumer.run
      }
    })
    sparkConsumer.start()

    val cassandraConsumer: MeetupCassandraConsumer = new
        MeetupCassandraConsumer("meetup")   //Topic name
    cassandraConsumer.run()
  }
}
