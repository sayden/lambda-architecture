package consumer

import com.google.gson.Gson
import storage.kundera.Meetup

object CountryCounters {
  private var countryCounters = collection.mutable.Map[String, Int]()
  private val gson: Gson = new Gson

  def meetupHandler(actual: String) {
    val meetup: Meetup = gson.fromJson(actual, classOf[Meetup])
    val country: String = meetup.group.group_country

    if(countryCounters.get(country) != None){
      countryCounters(country) += 1
    } else {
      countryCounters += (country -> 1)
    }

    printer(country, countryCounters(country))
  }

  def printer(tuple:(String, Int)) {
    val country:String = tuple._1
    val amount: Int = tuple._2
    val result: String = s"Country:$country, Results:$amount"
    println(result)
  }
}
