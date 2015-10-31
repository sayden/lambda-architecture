package consumer

import javax.persistence.EntityManager

import com.google.gson.Gson
import kafka.consumer._
import main.Constants
import storage.kundera.KunderaConnectorSingleton

import scala.collection.Map

/**
 * A Consumer to insert every message into a Cassandra DB
 * @param topic
 */
abstract class MeetupConsumer(topic: String) extends OnKafkaMessage{
  protected var em: EntityManager = null
  protected var gson: Gson = null

  /**
   * Configures a ConsumerConnector
   * @return consumerConnector ready to use
   */
  def getConsumerConnector: ConsumerConnector = {
    Consumer.create(new ConsumerConfig(Constants.getZookeeperProperties))
  }

  /**
   * Inits the consumer
   */
  def run(): Unit = {
    val consumer: ConsumerConnector = getConsumerConnector

    //Well just use one topic for this consumer
    var topicMap: Map[String, Int] = Map[String, Int]()
    topicMap += (topic -> 1)

    val consumerStreamsMap: Map[String, List[KafkaStream[Array[Byte],Array[Byte]]]] 
      = consumer.createMessageStreams(topicMap)

    val streamList: Option[List[KafkaStream[Array[Byte],Array[Byte]]]] 
      = consumerStreamsMap.get(topic)

    listen(streamList)
  }

  /**
   * Launch the consumer
   * @param optionalStreamList The stream list to listen
   */
  def listen(
      optionalStreamList: Option[List[KafkaStream[Array[Byte],Array[Byte]]]]):
      Unit = optionalStreamList
      
    match {
      case Some(streamList) =>
        em = KunderaConnectorSingleton.getEntityManager
        gson = new Gson
  
        for (stream <- streamList){
          val consumerIter = stream.iterator()

          //Iterate for every message
          while (consumerIter.hasNext()){
            val msg = consumerIter.next()
            val msgString = new String(msg.message())
                processMessage(msgString)
          }

        }
        KunderaConnectorSingleton.close
      
      case None => println("No streams found")
    }
}
