# README #

A project to integrate Meetup Streaming API through a Kafka Producer implemented in Nodejs, consumed by a Kakfa Consumer to store every message in a Cassandra NoSQL.
Cassandra will be configured to use Spark on it.

It has also a small Spark Streaming implementation to show some statistics over console.

## Setup ##

* Summary of set up
	* A local cassandra server
	* A local kafka broker
	* A "meetup" topic in the broker
	* Enter `src/main/nodejs` and execute `npm install` to install all node modules needed by the producer
	* Execute `babel-node meetupKafkaProducer.js` (NOTE: babel-node must be installed globally, you can do this by executing `npm install -g babel-node`. It can ask you for root access)

* File packages
	* **scala.consumer** Files of Kafka consumers:
		* **MeetupCassandraConsumer**: A Kafka Consumer for the "meetup" topic
		* **CountryCounters**: Object to store results about countries as well as static methods to use from the Spark consumer
		* **MeetupSparkConsumer**: An Spark Streaming consumer to store and prints results over console
		* **OnKafkaMessage**: Trait/Interface for MeetupConsumers
		* **MeetupConsumer**: Abstract class with the implementation needed by the Meetup topic in Kafka so that parsing implementation is trivial extending this class and using OnKafkaConsumer trait
	* **scala.main**
		* **Main**: Entrance point to the app
		* **Constants**: Some constants needed in the app
		* **SparkOverCassandra**: A simple example querying the data from Cassandra database
	* **scala.storage.kundera** Files about Kafka storage with Kundera library:
		* **KunderaConnectorSingleton** A Singleton to access an EntityManager to perform the persistence
		* **MeetupKundera** An object ready to insert into Cassandra using Kundera library
	* **scala.storage** Files about Kafka storage:
		* **Meetup** A POJO to perform Json-to-object transformations but without Kundera knowledge
	* **nodejs**
		* **meetupKafkaProducer** A producer configured to listen Meetup API and feed the Kafka broker.

It uses Kundera DDL Auto prepare to create any column family or table needed in Cassandra so there is no need of a creation script.
 
## Main files ##
Over the `src/main/main` package you can find the main objects. One is to 