package consumer

import main.Constants
import storage.Meetup
import storage.kundera.MeetupKundera

class MeetupCassandraConsumer(topic:String) extends MeetupConsumer(topic) with OnKafkaMessage{

  override def processMessage(msg: String): Unit = {
    saveInCassandra(msg)
  }

  /**
   * Stores a Meetup string message into a Cassandra Db
   * @param msg
   */
  def saveInCassandra(msg: String): Unit = {
    if (Constants.LOG) println(msg)

    val meetupPojo: Meetup = gson.fromJson(msg, classOf[Meetup])
    val meetup: MeetupKundera = new MeetupKundera
    if(meetupPojo!=null){
      meetup.convertPojoToCassandraKundera(meetupPojo)
      em.persist(meetup)
    }
  }
}
