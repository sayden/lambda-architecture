package consumer

import java.util.Properties

import kafka.consumer._

import scala.collection.Map


class MeetupConsumer(zookeeper: String, groupId: String, topic: String) {
  var kafkaHost: String = "localhost"
  var kafkaPort: Int = 9080

  def createConsumerConfig(): ConsumerConfig = {
    val props: Properties = new Properties()
    props.put("zookeeper.connect", zookeeper)
    props.put("group.id", groupId)
    props.put("zookeeper.session.timeout.ms", "400")
    props.put("zookeeper.sync.time.ms", "200")
    props.put("auto.commit.interval.ms", "1000")

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
        }
      }
    }
    case None => println("No streams found")
  }
}
