name := "scalaKafkaConsumer"

version := "1.0"

libraryDependencies += "org.apache.kafka" % "kafka-clients" % "0.8.2.2"

libraryDependencies += "org.apache.kafka" % "kafka_2.10" % "0.8.2.2"
//
//libraryDependencies += "org.json4s" % "json4s-native_2.10" % "3.3.0"
//
//libraryDependencies += "org.json4s" % "json4s-jackson_2.10" % "3.3.0"
//
//libraryDependencies += "net.liftweb" % "lift-webkit_2.10" % "3.0-M1"

libraryDependencies += "org.apache.httpcomponents" % "httpmime" % "4.0.3"

libraryDependencies += "com.datastax.cassandra" % "cassandra-driver-core" % "2.2.0-rc3"

libraryDependencies += "com.impetus.kundera.core" % "kundera-core" % "3.1"

libraryDependencies += "com.impetus.kundera.core" % "fallback-impl" % "3.1"

libraryDependencies += "org.scalatest" % "scalatest_2.10" % "3.0.0-M10"

libraryDependencies += "com.impetus.kundera.client" % "kundera-cassandra" % "3.1"

libraryDependencies += "com.impetus.kundera.client" % "kundera-cassandra-ds-driver" % "3.1"

libraryDependencies += "com.google.code.gson" % "gson" % "2.4"

libraryDependencies += "com.datastax.spark" % "spark-cassandra-connector_2.10" % "1.5.0-M2"

libraryDependencies += "org.apache.spark" % "spark-catalyst_2.10" % "1.5.1"

libraryDependencies += "org.apache.spark" % "spark-core_2.10" % "1.5.1"
