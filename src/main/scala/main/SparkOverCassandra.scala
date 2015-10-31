package main

import java.io.PrintWriter

import com.datastax.spark.connector._
import org.apache.spark.{SparkConf, SparkContext}

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
    val es = rdd.filter(_.getString("group_country") == "es")
      .map(row => row.getString("group_name") )
      .reduce(_ + "\n" + _)


    val result:String = s"count:$count \nfirst id:${first.getString("rsvp_id")} \nsum:$sum\n country:$es"
    new PrintWriter("/tmp/results") {
      write(result)
      close()
    }
  }
}