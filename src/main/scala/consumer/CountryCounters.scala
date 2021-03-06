package consumer

import com.google.gson.Gson
import storage.Meetup

object CountryCounters {
  private var countryCounters = collection.mutable.Map[String, Int]()
  private val gson: Gson = new Gson

  /**
   * Adds the new message to the country map
   * @param actual
   */
  def addResultToCountryAndPrints(actual: String) {
    val meetup: Meetup = gson.fromJson(actual, classOf[Meetup])
    val country: String = meetup.group.group_country

    countryCounters.get(country) match {
      case Some(a) => countryCounters(country) += 1
      case None => countryCounters += (country -> 1)
    }
    
    printer(country, countryCounters(country))
  }

  /**
   * Prints a tuple of type country -> result
   * @param tuple
   */
  def printer(tuple:(String, Int)) {
    val country:String = tuple._1
    val amount: Int = tuple._2
    val result: String = s"Country:$country, Results:$amount"
    println(result)
  }
}
