package org.azhur.kafka.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class CustomKafkaSerializer<T> implements Serializer<T> {

    private final ObjectMapper objectMapper;

    public CustomKafkaSerializer() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.disable(SerializationFeature.WRITE_NULL_MAP_VALUES);
        this.objectMapper.setSerializationInclusion(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL);
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // Ничего не нужно конфигурировать
    }

    @Override
    public byte[] serialize(String topic, T data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка сериализации объекта", e);
        }
    }

    @Override
    public void close() {
        // Ничего не нужно закрывать
    }
}

