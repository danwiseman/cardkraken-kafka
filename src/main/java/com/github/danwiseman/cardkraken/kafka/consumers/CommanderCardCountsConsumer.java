package com.github.danwiseman.cardkraken.kafka.consumers;

import static com.mongodb.client.model.Filters.eq;

import com.github.danwiseman.cardkraken.kafka.consumers.utils.EnvTools;
import com.mongodb.ConnectionString;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class CommanderCardCountsConsumer {

    private static final Logger log = LoggerFactory.getLogger(CommanderCardCountsConsumer.class);

    private static ConnectionString connectionString;
    private static MongoClient mongoClient;

    public static void main(String[] args) {

        Properties config = createProperties();

        String topic = EnvTools.getEnvValue(EnvTools.TOPIC, "commander-cards-counts");
        Integer minBatchSize = Integer.parseInt(EnvTools.getEnvValue(EnvTools.MIN_BATCH_SIZE, "15"));

        connectionString =
                new ConnectionString(EnvTools.getEnvValue(EnvTools.MONGODB_CONNECTION_STRING, "mongodb://AzureDiamond:hunter2@docker:27017/"));
        mongoClient = MongoClients.create(connectionString);

        config.setProperty(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName()
        );
        config.setProperty(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName()
        );
        config.setProperty(
                ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,
                "false"
        );

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(
                config
        );

        final Thread mainThread = Thread.currentThread();

        // add shutdown hook
        Runtime
                .getRuntime()
                .addShutdownHook(
                        new Thread() {
                            public void run() {
                                log.info("Detected a shutdown. Call consumer.wakeup()...");
                                consumer.wakeup();

                                // join main thread
                                try {
                                    mainThread.join();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                );

        try {
            consumer.subscribe(Arrays.asList(topic));
            List<ConsumerRecord<String, String>> buffer = new ArrayList<>();

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(
                        Duration.ofMillis(100)
                );

                for (ConsumerRecord<String, String> record : records) {
                    log.info("Key" + record.key());
                    buffer.add(record);
                }
                if (buffer.size() >= minBatchSize) {
                    for (ConsumerRecord<String, String> record : buffer) {
                        ConsumeCommanderCardCounts(record.key(), record.value());
                    }
                    consumer.commitSync();
                    buffer.clear();
                }
            }
        } catch (WakeupException e) {
            log.info("WakeupException");
        } catch (Exception e) {
            log.error("Unexpected exception", e);
        } finally {
            consumer.close();
            log.info("Consumer closed");
        }

    }

    private static void ConsumeCommanderCardCounts(String key, String value) {
        MongoDatabase database = mongoClient.getDatabase("commander_stats");
        MongoCollection<Document> documents = database.getCollection("card_counts");
        ReplaceOptions opts = new ReplaceOptions().upsert(true);

        try {
            JSONObject commanderCounts = new JSONObject(value);
            Bson query = eq("_id", commanderCounts.getString("stats_id"));
            commanderCounts.put("_id", commanderCounts.getString("stats_id"));
            UpdateResult insertResult = documents.replaceOne(query, new Document().parse(commanderCounts.toString()), opts);
            log.info("Upserted with id " + insertResult.getUpsertedId());
        } catch (MongoException me) {
            log.error("MongoException " + me);
        } catch (JSONException je) {
            log.error("JSONException on this: " + value);
        }
    }

    private static Properties createProperties() {
        Properties props = new Properties();
        String groupId = EnvTools.getEnvValue(EnvTools.GROUP_ID_CONFIG, "commander-card-counts-consumer");
        String bootstrapServersConfig = EnvTools.getEnvValue(EnvTools.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9092");
        String autoOffsetResetConfig = EnvTools.getEnvValue(EnvTools.AUTO_OFFSET_RESET_CONFIG, "earliest");

        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServersConfig);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetResetConfig);

        return props;

    }

}
