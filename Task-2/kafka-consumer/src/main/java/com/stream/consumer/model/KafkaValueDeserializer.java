package com.stream.consumer.model;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class KafkaValueDeserializer implements Deserializer<Category> {

	private static final Logger LOGGER =  LoggerFactory.getLogger(KafkaValueDeserializer.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public Category deserialize(String topic, byte[] data) {
        try {
        	Category cc = objectMapper.readValue(new String(data, "UTF-8"), Category.class);
            return cc;
        } catch (Exception e) {
        	LOGGER.error("Unable to deserialize message {}", data, e);
            return null;
        }
    }

    @Override
    public void close() {
    }

}
