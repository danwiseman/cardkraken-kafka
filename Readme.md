# Commander Card Count Streamer

Using Kafka Streams, it takes JSONs of a commander decks (formatted like below), and then adds the cards together
to create a count of how often the cards appear in commander decks with that specific commander.

:point_right: Input JSON:
```json
{
  "deck_id": "043fbae9-0d58-4d72-8dfa-a1254f13a721",
  "game_type": "edh",
  "source": "deck source",
  "url": "https://decksource",
  "cards": [
    {
      "id": "6a28dd88-db90-4f02-8aa9-39051d2c4763",
      "qty": "1",
      "name": "Accelerate"
    },
   ...
    {
      "id": " ",
      "qty": "1",
      "name": "Zada, Hedron Grinder"
    }
  ],
  "commanders": [
    "General Tazri"
  ]
}
```

:point_left: Output KTable:
```json
{
  "commander_name": "[General Tazri]",
  "commander_uuid": "",
  "card_counts": [
    {
      "id": "6a28dd88-db90-4f02-8aa9-39051d2c4763",
      "qty": "6",
      "name": "Accelerate"
    },
    ...
    {
      "id": " ",
      "qty": "2",
      "name": "Zada, Hedron Grinder"
    }
  ]
}
```


Thanks :pray: to Arturo for this article and the serialization of POJOs: 
[JSON Serializers for POJOs](https://medium.com/@agvillamizar/implementing-custom-serdes-for-java-objects-using-json-serializer-and-deserializer-in-kafka-streams-d794b66e7c03)