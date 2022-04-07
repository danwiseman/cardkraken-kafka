package com.github.danwiseman.cardkraken.kafka.streams;

import com.github.danwiseman.cardkraken.kafka.consumers.CommanderCardCountsConsumer;
import com.github.danwiseman.cardkraken.kafka.streams.model.CommanderCardsStats;
import com.github.danwiseman.cardkraken.kafka.streams.model.CommanderDeck;
import com.github.danwiseman.cardkraken.kafka.streams.utils.EnvTools;
import com.github.f4b6a3.uuid.UuidCreator;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;

public class CommanderCardCountsStream {

    private static final Logger log = LoggerFactory.getLogger(CommanderCardCountsStream.class);

    public static void main(String[] args) {

        Properties config = createProperties();

        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        String inputTopic = EnvTools.getEnvValue(EnvTools.INPUT_TOPIC, "commander-decks-input");
        String outputTopic = EnvTools.getEnvValue(EnvTools.OUTPUT_TOPIC, "commander-cards-counts");


        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, CommanderDeck> commanderDecksInput =
                builder.stream(inputTopic, Consumed.with(Serdes.String(), CustomSerdes.CommanderDeck()));


        KTable<String, CommanderCardsStats> commanderCardCounts = commanderDecksInput
                .selectKey((oldKey, newKey) -> generateCommanderCountUUID(newKey.getCommanders_uuids()))
                .mapValues((deck) -> new CommanderCardsStats(deck.getCommanders(), deck.getCommanders_uuids(), generateCommanderCountUUID(deck.getCommanders_uuids()), deck.getCards()))
                .groupByKey(Grouped.with(Serdes.String(), CustomSerdes.CommanderCardsStats()))
                .reduce((deck1, deck2) -> {
                    deck1.addCards(deck2.getCard_counts());
                    CommanderCardsStats aggDeck = new CommanderCardsStats(
                            deck1.getCommanders(),
                            deck1.getCommander_uuids(),
                            deck1.getStats_id(),
                            deck1.getCard_counts()
                    );
                    return aggDeck;
                });

        commanderCardCounts.toStream().to(outputTopic, Produced.with(Serdes.String(), CustomSerdes.CommanderCardsStats()));

        KafkaStreams streams = new KafkaStreams(builder.build(), config);

        streams.cleanUp();
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

    }

    private static String generateCommanderCountUUID(List<String> commanders_uuids) {
        log.error(commanders_uuids.toString());
        if (commanders_uuids.size() == 1) {
            return commanders_uuids.get(0);
        } else {
            return UuidCreator.getNameBasedSha1(generateCommanderKey(commanders_uuids)).toString();
        }
    }

    private static Properties createProperties() {
        Properties props = new Properties();
        String appIdConfig = EnvTools.getEnvValue(EnvTools.APPLICATION_ID_CONFIG, "commander-card-counts-app");
        String bootstrapServersConfig = EnvTools.getEnvValue(EnvTools.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9092");
        String autoOffsetResetConfig = EnvTools.getEnvValue(EnvTools.AUTO_OFFSET_RESET_CONFIG, "earliest");

        props.put(StreamsConfig.APPLICATION_ID_CONFIG, appIdConfig);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServersConfig);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetResetConfig);

        return props;

    }

    private static String generateCommanderKey(List<String> commanders_uuids) {
        return commanders_uuids.toString();
    }


}
