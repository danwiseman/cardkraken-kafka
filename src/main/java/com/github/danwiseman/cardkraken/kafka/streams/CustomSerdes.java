package com.github.danwiseman.cardkraken.kafka.streams;


import com.github.danwiseman.cardkraken.kafka.streams.model.CommanderCardsStats;
import com.github.danwiseman.cardkraken.kafka.streams.model.CommanderDeck;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

public final class CustomSerdes {

  private CustomSerdes() {}



  public static Serde<CommanderDeck> CommanderDeck() {
    JsonSerializer<CommanderDeck> serializer = new JsonSerializer<>();
    JsonDeserializer<CommanderDeck> deserializer = new JsonDeserializer<>(
      CommanderDeck.class
    );
    return Serdes.serdeFrom(serializer, deserializer);
  }

  public static Serde<CommanderCardsStats> CommanderCardsStats() {
    JsonSerializer<CommanderCardsStats> serializer = new JsonSerializer<>();
    JsonDeserializer<CommanderCardsStats> deserializer = new JsonDeserializer<>(
            CommanderCardsStats.class
    );
    return Serdes.serdeFrom(serializer, deserializer);
  }
  /*
    public static Serde<GenreCount> GenreCount() {
        JsonSerializer<GenreCount> serializer = new JsonSerializer<>();
        JsonDeserializer<GenreCount> deserializer = new JsonDeserializer<>(GenreCount.class);
        return Serdes.serdeFrom(serializer, deserializer);
    }
*/

}
