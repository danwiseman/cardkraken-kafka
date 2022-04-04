
FROM openjdk:8-alpine AS commander_count_stream

WORKDIR /app/
COPY target/commander-card-counts-streams-*.jar .
ENTRYPOINT ["java", "-cp", "*", "com.github.danwiseman.cardkraken.kafka.streams.CommanderCardCountsStream"]


FROM openjdk:8-alpine AS commander_count_consumer

WORKDIR /app/
COPY target/commander-card-counts-consumer-*.jar .
ENTRYPOINT ["java", "-cp", "*", "com.github.danwiseman.cardkraken.kafka.consumers.CommanderCardCountsConsumer"]

