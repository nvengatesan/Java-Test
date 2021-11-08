package com.stream.consumer;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.ForeachAction;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KGroupedStream;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.KeyValueStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.support.serializer.JsonSerde;

import com.stream.consumer.model.Category;


@Configuration
@EnableKafka
@EnableKafkaStreams
public class KafkaStream {
	
	private static final Logger LOGGER =  LoggerFactory.getLogger(KafkaStream.class);
	private static final String TOPIC = "categories";
	private static final String OUTPUT_TOPIC = "summaryByCategory";
	
	@Autowired
    public void kStreamJson(StreamsBuilder builder) {
		final KStream<String, Category> stream = builder.stream(TOPIC, Consumed.with(Serdes.String(), new JsonSerde<>(Category.class)));
    	final KGroupedStream<String, Double> groupedStream =  stream.map(new TestKeyValueMapper()).groupByKey(Grouped.with(Serdes.String(), Serdes.Double()));
    	final KTable<String, Double> aggregate = groupedStream
        									.aggregate(() -> 0D
        											,(key, newValue, aggValue) -> aggValue + newValue,
        											Materialized.<String, Double, KeyValueStore<Bytes, byte[]>>as("sum"));
    	
    	
    	// Logs
    	aggregate.toStream().foreach(new ForeachAction<String, Double>() {

			@Override
			public void apply(String key, Double value) {
				LOGGER.debug("key = {} and value = {}" ,key, value);
			}
		});
    	
    	aggregate.toStream().to(OUTPUT_TOPIC, Produced.with(Serdes.String(), Serdes.Double()));
    	
    	// Used Kafka Connect to push the content from kafka topic to DB Summary table
    	
    }
	
	
	public static class TestKeyValueMapper implements KeyValueMapper<String, Category, KeyValue<String, Double>> {

		@Override
		public KeyValue<String, Double> apply(String key, Category value) {
			return new KeyValue<String, Double>(value.getCategory(), Double.parseDouble(value.getValue()));
		}
		
    }
}
