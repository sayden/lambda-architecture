package KafkaConsumer

import java.util.Properties

final object Constants {
  val PERSISTENCE_UNIT_NAME: String = "cassandra_pu"
  val KEYSPACE_NAME: String = "kunderaexamples"
  val SCHEMA_NAME: String = s"$KEYSPACE_NAME@$PERSISTENCE_UNIT_NAME"

  object Tables {
    val MEETUP: String = "meetup"
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
