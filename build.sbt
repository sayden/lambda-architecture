name := "scalaKafkaConsumer"

version := "1.0"

libraryDependencies += "org.apache.kafka" % "kafka-clients" % "0.8.2.2"

libraryDependencies += "org.apache.kafka" % "kafka_2.10" % "0.8.2.2"

libraryDependencies += "org.json4s" % "json4s-native_2.10" % "3.3.0"

libraryDependencies += "org.json4s" % "json4s-jackson_2.10" % "3.3.0"

libraryDependencies += "net.liftweb" % "lift-webkit_2.10" % "3.0-M1"

libraryDependencies += "org.apache.httpcomponents" % "httpmime" % "4.0.3"

libraryDependencies += "com.datastax.cassandra" % "cassandra-driver-core" % "2.2.0-rc3"

val PhantomVersion = "1.12.2"

resolvers ++= Seq(
  "Typesafe repository snapshots" at "http://repo.typesafe.com/typesafe/snapshots/",
  "Typesafe repository releases" at "http://repo.typesafe.com/typesafe/releases/",
  "Sonatype repo"                    at "https://oss.sonatype.org/content/groups/scala-tools/",
  "Sonatype releases"                at "https://oss.sonatype.org/content/repositories/releases",
  "Sonatype snapshots"               at "https://oss.sonatype.org/content/repositories/snapshots",
  "Sonatype staging"                 at "http://oss.sonatype.org/content/repositories/staging",
  "Java.net Maven2 Repository"       at "http://download.java.net/maven/2/",
  "Twitter Repository"               at "http://maven.twttr.com",
  Resolver.bintrayRepo("websudos", "oss-releases")
)

libraryDependencies ++= Seq(
  "com.websudos" %% "phantom-dsl" % PhantomVersion,
  "com.websudos" %% "phantom-zookeeper" % PhantomVersion,
  "com.websudos" %% "phantom-testkit" % PhantomVersion % "test, provided"
)