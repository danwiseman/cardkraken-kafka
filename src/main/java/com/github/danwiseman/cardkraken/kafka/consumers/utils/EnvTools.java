package com.github.danwiseman.cardkraken.kafka.consumers.utils;

// Edited from Article: https://medium.com/swlh/dockerizing-a-kafka-streams-app-6a5ea71fe1ef
public class EnvTools {
    public static final String TOPIC = "TOPIC";
    public static final String GROUP_ID_CONFIG = "GROUP_ID_CONFIG";
    public static final String BOOTSTRAP_SERVERS_CONFIG = "BOOTSTRAP_SERVERS_CONFIG";
    public static final String AUTO_OFFSET_RESET_CONFIG = "AUTO_OFFSET_RESET_CONFIG";
    public static final String MIN_BATCH_SIZE = "MIN_BATCH_SIZE";
    public static final String MONGODB_CONNECTION_STRING = "MONGODB_CONNECTION_STRING";

    public static String getEnvValue(String environmentKey, String defaultValue)
    {
        String envValue = System.getenv(environmentKey);
        if(envValue != null && !envValue.isEmpty())
        {
            return envValue;
        }
        return defaultValue;
    }
}