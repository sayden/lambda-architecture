package consumer

import java.util.Properties

import KafkaConsumer.Constants
import kafka.consumer._
import storage.MeetupParser

import scala.collection.Map


class MeetupConsumer(topic: String) {
  def createConsumerConfig(): ConsumerConfig = {
    val props: Properties = Constants.getZookeeperProperties
    new ConsumerConfig(props)
  }

  def run(): Unit = {
    val consumer: ConsumerConnector = Consumer.create(createConsumerConfig())

    var topicMap: Map[String, Int] = Map[String, Int]()
    topicMap += (topic -> 1)

    val consumerStreamsMap: Map[String, List[KafkaStream[Array[Byte],Array[Byte]]]] = consumer.createMessageStreams(topicMap)

    val optionalStreamList: Option[List[KafkaStream[Array[Byte],Array[Byte]]]] = consumerStreamsMap.get(topic)

    resolveOptionalStreamList(optionalStreamList)
  }

  def resolveOptionalStreamList(optionalStreamList: Option[List[KafkaStream[Array[Byte],Array[Byte]]]]):
  Unit = optionalStreamList match {
    case Some(streamList) => {
      for (stream <- streamList){
        val consumerIter = stream.iterator

        while (consumerIter.hasNext){
          val msg = consumerIter.next()
          val msgString = new String(msg.message())
          println("Message from Single Topic :: " + msgString)
          val meetup = MeetupParser.parseString(msgString)

        }
      }
    }
    case None => println("No streams found")
  }
}
