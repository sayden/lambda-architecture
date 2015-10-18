package storage

import com.datastax.driver.core.Session
import com.websudos.phantom.connectors.{ContactPoints, KeySpace, SimpleConnector}
import com.websudos.phantom.zookeeper.ZkContactPointLookup

trait KeyspaceDefinition {
  implicit val keySpace = KeySpace("usersKs")
}

/**
 * This is an example of how to connect to Cassandra in the easiest possible way.
 * The SimpleCassandraConnector is designed to get you up and running immediately, with almost 0 effort.
 *
 * What you have to do now is to tell phantom what keyspace you will be using in Cassandra. This connector will automaticalyl try to connect to localhost:9042.
 * If you want to tell the connector to use a different host:port combination, simply override the address inside it.
 *
 * Otherwise, simply mixing this connector in will magically inject a database session for all your queries and you can immediately run them.
 */
trait ExampleConnector extends SimpleConnector with KeyspaceDefinition

/**
 * Now you might ask yourself how to use service discovery with phantom. The Datastax Java Driver can automatically connect to multiple clusters.
 * Using some underlying magic, phantom can also help you painlessly connect to a series of nodes in a Cassandra cluster via ZooKeeper.
 *
 * Once again, all you need to tell phantom is what your keyspace is. Phantom will make a series of assumptions about which path you are using in ZooKeeper.
 * By default, it will try to connect to localhost:2181, fetch the "/cassandra" path and parse ports found in a "host:port, host1:port1,
 * .." sequence. All these settings are trivial to override in the below connector and you can adjust all the settings to fit your environment.
 */
object ZkDefaults extends KeyspaceDefinition {
  val connector = ZkContactPointLookup.local.keySpace(keySpace.name)
}

trait DefaultZookeeperConnector extends SimpleConnector {
  override implicit lazy val session: Session = ZkDefaults.connector.session
}


/**
 * This is an example of how to connect to a custom set of hosts and ports.
 * First, we need to obtain a connector and keep a singleton reference to it.
 * It's really important to guarantee we are using a singleton here, otherwise
 * we will end up spawning a cluster on every call.
 */
object RemoteConnector extends KeyspaceDefinition {

  // Simply specify the list of hosts followed by the keyspace.
  // Now the connector object will automatically create the Database connection for us and initialise it.
  val connector = ContactPoints(Seq("localhost")).keySpace("usersKs")
}

trait DockerConnector extends RemoteConnector.connector.Connector