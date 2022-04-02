package com.github.danwiseman.cardkraken.kafka.streams;

import com.github.danwiseman.cardkraken.kafka.streams.model.CommanderCardsStats;
import com.github.danwiseman.cardkraken.kafka.streams.model.CommanderDeck;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;

import java.util.List;
import java.util.Properties;

public class CommanderCardCountsStream {

    public static void main(String[] args) {

        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "commander-card-counts-app");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9092");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, CommanderDeck> commanderDecksInput =
                builder.stream("commander-decks-input", Consumed.with(Serdes.String(), CustomSerdes.CommanderDeck()));


        KTable<String, CommanderCardsStats> commanderCardCounts = commanderDecksInput
                .selectKey((oldKey, newKey) -> generateCommanderKey(newKey.getCommanders()))
                .mapValues((deck) -> new CommanderCardsStats(generateCommanderKey(deck.getCommanders()).toString(), "", deck.getCards()))
                .groupByKey(Grouped.with(Serdes.String(), CustomSerdes.CommanderCardsStats()))
                .reduce((deck1, deck2) -> {
                    deck1.addCards(deck2.getCard_counts());
                    CommanderCardsStats aggDeck = new CommanderCardsStats(
                            deck1.getCommander_name(),
                            deck1.getCommander_uuid(),
                            deck1.getCard_counts()
                    );
                    return aggDeck;
                });

        commanderCardCounts.toStream().to("commander-cards-counts", Produced.with(Serdes.String(), CustomSerdes.CommanderCardsStats()));

        KafkaStreams streams = new KafkaStreams(builder.build(), config);

        streams.cleanUp();
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

    }


    private static String generateCommanderKey(List<String> commanders) {
        return commanders.toString();
    }
}
