package com.stream.producer.core;

import java.util.List;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.stream.producer.model.Category;

@Component
public class KafkaWriter implements ItemWriter<Category>{

	@Value("${kafka_topic}")
    private String jsonTopic;
	
    @Autowired
    private KafkaTemplate<String, Category> kafkaTemplate;

    @Override
    public void write(List<? extends Category> categories) throws Exception {
    	for(Category category : categories) {
    		ProducerRecord<String, Category> producerRecord = new ProducerRecord<>(jsonTopic,category.getCategory().toLowerCase(), category);
    		kafkaTemplate.send(producerRecord);
    	}
    }
}
