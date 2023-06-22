import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.simple.JSONObject;

import java.util.Properties;
/**
 * Kafka Example 2023
 *
 * */
public class KafkaJsonProducer {

    public static void main(String[] args){
        // Set Kafka producer properties
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");


        // Create a Kafka Producer
        KafkaProducer<String,String> producer = new KafkaProducer<>(properties);

        JSONObject json = new JSONObject();
        json.put("name", "John");
        json.put("age", 30);
        json.put("city", "New York");

        ProducerRecord<String, String> record = new ProducerRecord<>("my_topic", json.toJSONString());

        producer.send(record,(metadata, exception) -> {
           if(exception != null){
               exception.printStackTrace();
           } else {
               System.out.println("Message SENT successfully. Offset: " + metadata.offset());
           }
        });


        producer.close();

    }
}
