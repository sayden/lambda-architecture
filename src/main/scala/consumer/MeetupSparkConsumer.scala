package consumer

import _root_.kafka.serializer.StringDecoder
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD

import main.Constants
import storage.kundera.Meetup
import com.google.gson.Gson


/**
 * A Consumer to insert every message into a Cassandra DB
 * @param topic
 */
class MeetupSparkConsumer(topic: String) extends Serializable {
  private var conf: SparkConf = null
  private var ssc: StreamingContext = null
  private var gson: Gson = new Gson

  def run: Unit = {
    if (conf == null) {
      conf = new SparkConf(true)
      conf.setAppName("spark-streaming-example")
      conf.setMaster("local")

      ssc = new StreamingContext(conf, Seconds(10))

      val topicsSet: Set[String] = Set("meetup")
      val kafkaParams: Map[String, String] = Map("metadata.broker.list" -> "localhost:9092", "group.id" -> "1")
      val rawDstream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topicsSet)

//      val jsonString: String = rawDstream.map(_._2).toString
      rawDstream.map(_._2).foreachRDD {rdd:RDD[String] =>
        rdd.foreach(CountryCounters.meetupHandler)
      }


        /*
        val meetupPojo: Meetup = gson.fromJson(rdd, classOf[Meetup])
        println(jsonString)
        val country: String = meetupPojo.group.group_country

        if(countryCounters.get(country) != None){
          countryCounters(country) += 1
          println(countryCounters)
        } else {
          countryCounters += (country -> 1)
          println(countryCounters)
        }
        */

      ssc.start()
      ssc.awaitTermination()
    }
  }
}
