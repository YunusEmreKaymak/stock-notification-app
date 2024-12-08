package com.yunus.stock_ingestion_service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

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
        return new Serializer<T>() {
            @Override
            public byte[] serialize(String topic, T data) {
                try {
                    return objectMapper.writeValueAsBytes(data);
                } catch (Exception e) {
                    throw new RuntimeException("Serialization error", e);
                }
            }

            @Override
            public void configure(Map<String, ?> configs, boolean isKey) {
            }

            @Override
            public void close() {
            }
        };
    }

    @Override
    public Deserializer<T> deserializer() {
        return new Deserializer<T>() {
            @Override
            public T deserialize(String topic, byte[] data) {
                try {
                    return objectMapper.readValue(data, targetType);
                } catch (Exception e) {
                    throw new RuntimeException("Deserialization error", e);
                }
            }

            @Override
            public void configure(Map<String, ?> configs, boolean isKey) {
            }

            @Override
            public void close() {
            }
        };
    }
}
