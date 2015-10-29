package spark

import java.io.PrintWriter

import org.apache.spark.{SparkConf, SparkContext}
import com.datastax.spark.connector._

object SparkOverCassandra {
  def main(args: Array[String]) {
    val conf = new SparkConf(true)
    conf.setAppName("cassandra-example-hello")
    conf.setMaster("local")
    conf.set("spark.cassandra.connection.host", "127.0.0.1")

    val spark = new SparkContext("local", "test", conf)

    val rdd = spark.cassandraTable("kunderaexamples", "meetup")
    val count = rdd.count
    val first = rdd.first
    val sum = rdd.map(_.getInt("guests")).sum

    val result:String = s"count:$count \nfirst id:${first.getString("rsvp_id")} \nsum:$sum"
    new PrintWriter("/tmp/results") {
      write(result)
      close()
    }
  }
}