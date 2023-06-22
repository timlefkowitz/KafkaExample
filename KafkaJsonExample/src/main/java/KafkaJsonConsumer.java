import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaJsonConsumer {

    public static void main(String[] args){

        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092" );
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "my_group");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());


        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
        consumer.subscribe(Collections.singleton("my_topic"));

        while(true){
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

            for(ConsumerRecord<String, String> record : records){
                try {

                    // Parse JSON message
                    JSONParser parser = new JSONParser();
                    JSONObject json = (JSONObject) parser.parse(record.value());

                    // Process JSON data
                    String name = (String) json.get("name");
                    int age = ((Long) json.get("age")).intValue();
                    String city = (String) json.get("city");

                    // Print the process data
                    System.out.println("Name: "+name+" , Age: " + age + "City: " + city);
                } catch (ParseException e){
                    e.printStackTrace();
                }
            }

        }
    }
}
