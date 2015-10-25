package storage

import com.datastax.driver.core.{Cluster, Metadata, Session}

class CassandraJavaScalaStorage {
  private var cluster: Cluster = null
  private var session: Session = null

  def run(): Unit ={
    this.connect("localhost")
    this.loadData
    this.close
  }

  def connect(node: String) {
    cluster = Cluster.builder.addContactPoint(node).build
    val metadata: Metadata = cluster.getMetadata
    printf("Cluster: %s\n", metadata.getClusterName)
    import scala.collection.JavaConversions._
    for (host <- metadata.getAllHosts) {
      printf("Host: %s \n", host.getAddress)
    }
    session = cluster.connect
    session.execute("USE mykeyspace")
  }

  def close {
    cluster.close
  }

  def loadData {
    session.execute("INSERT INTO usersks.users (user_id, fname, lname) VALUES (c37d661d-7e61-49ea-96a5-68c34e83db3a, 'Mario', 'Castro')")
  }

  def insertData(query: String): Unit = {
    session.execute(query)
  }
}