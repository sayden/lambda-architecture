import Meetup from "meetup";
let mup = new Meetup();
import kafka from 'kafka-node';

let Producer = kafka.Producer;
let kafkaClient = new kafka.Client();
let producer = new Producer(kafkaClient);

producer.on('ready', () => {
  mup.stream("/2/rsvps", stream => {
    console.log("stream opened");

    stream.on("data", item => {
        console.log(item);
        //Pipe it to Kafka
        producer.send([{
          topic:'meetup',
          messages:JSON.stringify(item)
        }], (err) => {
          if(err)
            console.log("Error when sending to kafka", err);
        });

    }).on("error", e => {
       console.log("error! " + e)
    });
  });
});
