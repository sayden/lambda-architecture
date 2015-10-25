package storage.kundera

import javax.persistence._

@Entity
@Table (name = "meetup", schema="kunderaexamples@cassandra_pu ")
class MeetupKundera {

  @Id
  private var rsvp_id: String = null

  @Column
  private var guests: Int = 0

  @Column
  private var visibility:String = null

  @Column (name = "venue_name")
  private var venueName: String = null

  @Column (name = "venue_lon")
  private var venueLon: String = null

  @Column (name = "venue_lat")
  private var venueLat: String = null

  @Column (name = "venue_id")
  private var venueId: String = null


  def getVisibility = visibility
  def setVisibility(vis: String) = visibility = vis
  def setGuest(_guests: Int) = guests = _guests
  def getGuests = guests
  def setVenueName(_venueName:String) = venueName = _venueName
  def getVenueName: String = venueName
  def setVenueLon(_lon: String) = venueLon = _lon
  def getVenueLon: String = venueLon
  def setVenueLat(_lat: String) = venueLat = _lat
  def getVenueLat: String = venueLat
  def getId = rsvp_id
  def setId(_id:String) = rsvp_id = _id
  def setVenueId(_id: String) = venueId = _id
  def getVenueId: String = venueId
}
