package spark

import org.apache.spark._
import com.datastax.spark.connector._

object SparkMain {
  def main (args: Array[String]): Unit = {
    // only setting app name, all other properties will be specified at runtime for flexibility
    val conf = new SparkConf(true)
    conf.setAppName("cassandra-example-hello")
    conf.set("spark.cassandra.connection.host", "127.0.0.1")
    conf.setMaster("local")

    val sc = new SparkContext("spark://localhost:7077", "test", conf)

    val hello = sc.cassandraTable[(String, String)]("test", "hello")

    val first = hello.first

    sc.stop

    println(first)
  }
}
