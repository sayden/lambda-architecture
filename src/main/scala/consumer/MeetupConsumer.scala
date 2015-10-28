package consumer

import java.util.Properties

import KafkaConsumer.Constants
import com.google.gson.Gson
import kafka.consumer._
import storage.kundera.{KunderaConnectorSingleton, Meetup, MeetupKundera}

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
      val em = KunderaConnectorSingleton.getEntityManager
      val gson: Gson = new Gson

      for (stream <- streamList){
        val consumerIter = stream.iterator

        while (consumerIter.hasNext){
          val msg = consumerIter.next()
          val msgString = new String(msg.message())
          println("Message from Single Topic :: " + msgString)

          val json:Meetup = gson.fromJson(msgString, classOf[Meetup])
          val meetupKundera: MeetupKundera = new MeetupKundera
          if(json!=null){
            meetupKundera.flatten(json)
            em.persist(meetupKundera)
          }
        }
      }
      KunderaConnectorSingleton.close
    }
    case None => println("No streams found")
  }
}
