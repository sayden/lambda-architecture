package consumer;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class MyJavaKafkaConsumer {
    private final ConsumerConnector consumer;
    private final String topic;

    public MyJavaKafkaConsumer(String zookeeper, String groupId, String topic) {
        this.consumer = kafka.consumer.Consumer
                .createJavaConsumerConnector(createConsumerConfig(zookeeper, groupId));
        this.topic = topic;
    }

    private static ConsumerConfig createConsumerConfig(String zookeeper,
                                                       String groupId) {
        Properties props = new Properties();
        props.put("zookeeper.connect", zookeeper);
        props.put("group.id", groupId);
        props.put("zookeeper.session.timeout.ms", "500");
        props.put("zookeeper.sync.time.ms", "250");
        props.put("auto.commit.interval.ms", "1000");
        return new ConsumerConfig(props);
    }

    public void testConsumer() {
        Map<String, Integer> topicMap = new HashMap<String, Integer>();

        //	Define	single	thread	for	topic
        topicMap.put(topic, new Integer(1));

        Map<String, List<KafkaStream<byte[], byte[]>>> consumerStreamsMap = consumer.createMessageStreams(topicMap);
        List<KafkaStream<byte[], byte[]>> streamList = consumerStreamsMap.get(topic);

        for (final KafkaStream<byte[], byte[]> stream : streamList) {
            ConsumerIterator<byte[], byte[]> consumerIte = stream.iterator();
            while (consumerIte.hasNext())
                System.out.println("Message	from	Single	Topic	::	" + new String(consumerIte.next().message()));
        }

        if (consumer != null)
            consumer.shutdown();
    }
}
