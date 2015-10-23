import org.scalatest.{FlatSpec, Matchers}
import storage.models._

class MeetupTest extends FlatSpec with Matchers {
  implicit val formats = net.liftweb.json.DefaultFormats

  "A GroupTopic" should "be converted into String" in {

    val gp1: GroupTopic = new GroupTopic("name1", "key1")
    val gp2: GroupTopic = new GroupTopic("name2", "key2")
    val gp3: GroupTopic = new GroupTopic("name3", "key3")

    val gTopics: List[GroupTopic] = List(gp1, gp2, gp3)

    val meetup:Meetup = new Meetup("", "", 0, "", 0, new Venue("", 0, 0, 0),
      new Member(0, ""), new Event("", "", "", ""), new Group("", "", 0, "", 0,
        "", "", 0, gTopics))
    var res = meetup.groupTopicsToString(gTopics)

    res = meetup.toInsertQuery(meetup)

    println(res)
  }
}
