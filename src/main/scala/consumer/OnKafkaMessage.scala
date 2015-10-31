package consumer

trait OnKafkaMessage {
  def processMessage(msg:String)
}
