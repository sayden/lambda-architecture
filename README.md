# README #

A project to integrate a node source through a kafka message broker Producer, consumed by a Kakfa Consumer to store it in a Cassandra nosql and another kafka consumer to spark stream it

### How do I get set up? ###

* Summary of set up
	* With the current configuration, a local cassandra server
	* With the current configuration, a local kafka broker

* File packages
	* *consumer* Files of Kafka consumers:
		*	*JsonParserTest*: Some methods to try a json parser
		* *Main*: Entrance point to the app
		* *MeetupConsumer*: A Kafka Consumer for the "meetup" topic
		* *ScalaTutorials*: A set of tutorials about scala
	* *storage* Files about Kafka storage:
		* *CassandraJavaStorage*: A java implementation to insert a record in cassandra
		* *ExampleConnector*: Minimum acceptable connector for scala phantom driver to access cassandra
		* *SimpleUser*: An full implementation of cassandra access using scala phantom library. Depends on storage.User class
		* *User*: User POJO