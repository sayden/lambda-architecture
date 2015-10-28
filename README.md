# README #

A project to integrate Meetup Streaming API through a Kafka Producer implemented in Nodejs, consumed by a Kakfa Consumer to store every message in a Cassandra NoSQL.
Cassandra will be configured to use Spark on it.
A consumer will also be written to use with Spark Streaming.

### Setup ###

* Summary of set up
	* A local cassandra server
	* A local kafka broker

* File packages
	* **scala.consumer** Files of Kafka consumers:
		* **MeetupConsumer**: A Kafka Consumer for the "meetup" topic
	* **scala.main**
		* **Main**: Entrance point to the app
		* **Constants**: Some constants needed in the app
	* **scala.storage.kundera** Files about Kafka storage:
		* **KunderaConnectorSingleton** A Singleton to access an EntityManager to perform the persistence
		* **Meetup** A POJO to perform Json-to-object transformations but without Kundera knowledge
		* **MeetupKundera** An object ready to insert into Cassandra using Kundera library
	* **nodejs**
		* **meetupKafkaProducer** A producer configured to listen Meetup API and feed the Kafka broker.

It uses Kundera DDL Auto prepare to create any column family or table needed in Cassandra so there is no need of a creation script. 