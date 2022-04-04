
FROM openjdk:8-alpine AS RUNTIME

WORKDIR /app/
COPY target/commander-cardcounts-*-jar-with-dependencies.jar .
ENTRYPOINT ["java", "-cp", "*", "com.github.danwiseman.cardkraken.kafka.streams.CommanderCardCountsStream"]