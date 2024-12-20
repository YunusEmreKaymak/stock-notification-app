package com.yunus.stock_ingestion_service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class CustomSerde<T> implements Serde<T> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Class<T> targetType;

    public CustomSerde() {
    }

    public CustomSerde(Class<T> targetType) {
        this.targetType = targetType;
    }

    @Override
    public Serializer<T> serializer() {
        return (topic, data) -> {
            try {
                return objectMapper.writeValueAsBytes(data);
            } catch (Exception e) {
                throw new RuntimeException("Serialization error", e);
            }
        };
    }

    @Override
    public Deserializer<T> deserializer() {
        return (topic, data) -> {
            try {
                return objectMapper.readValue(data, targetType);
            } catch (Exception e) {
                throw new RuntimeException("Deserialization error", e);
            }
        };
    }
}
