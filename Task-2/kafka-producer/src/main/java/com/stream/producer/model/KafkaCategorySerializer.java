package com.stream.producer.model;

import java.util.Map;

import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class KafkaCategorySerializer implements Serializer<Category> {

	private static final Logger LOGGER =  LoggerFactory.getLogger(KafkaCategorySerializer.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, Category data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
        	LOGGER.error("Unable to serialize object {}", data, e);
            return null;
        }
    }

    @Override
    public void close() {
    }

}
