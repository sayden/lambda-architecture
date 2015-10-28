package main

import java.util.Properties

object Constants {
  final val PERSISTENCE_UNIT_NAME = "cassandra_pu"
  final val KEYSPACE_NAME = "kunderaexamples"
  final val SCHEMA_NAME = KEYSPACE_NAME + "@" + PERSISTENCE_UNIT_NAME
  final val LOG = true

  object Tables {
    final val MEETUP = "meetup"
  }

  def getZookeeperProperties: Properties = {
    val props: Properties = new Properties
    props.put("zookeeper.connect", "localhost:2181")
    props.put("group.id", "1")
    props.put("zookeeper.session.timeout.ms", "400")
    props.put("zookeeper.sync.time.ms", "200")
    props.put("auto.commit.interval.ms", "1000")
    props
  }

  object Kafka {
    val KAFKA_HOST: String = "localhost"
    val KAFKA_PORT: Int = 9080
  }
}
