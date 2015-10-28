package storage.kundera

import java.util
import javax.persistence.{EntityManager, Persistence, EntityManagerFactory}
import main.Constants
import com.impetus.client.cassandra.common.CassandraConstants

/**
 * A singleton that stores the EntityManager and manages also the Factory
 */
object KunderaConnectorSingleton {
  private var em: EntityManager = null
  private var emf: EntityManagerFactory = null

  def getEntityManager:EntityManager = {
    if(em == null) {
      val puProperties: util.HashMap[String, Object] = new util.HashMap[String, Object]()
      puProperties.put(CassandraConstants.CQL_VERSION, CassandraConstants.CQL_VERSION_3_0)
      emf = Persistence.createEntityManagerFactory(Constants.PERSISTENCE_UNIT_NAME, puProperties)
      em = emf.createEntityManager()
    }
    em
  }

  def close(): Unit = {
    em.close()
    emf.close()
  }
}
