package com.stream.producer;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.kafka.annotation.EnableKafka;

import com.stream.producer.core.KafkaWriter;
import com.stream.producer.model.Category;


@Configuration
@EnableBatchProcessing
@EnableKafka
public class BatchConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private KafkaWriter kafkaWriter;
	
	private static final String FILENAME = "category-data.csv";
	
	@Bean
	public Job createCategoryJob() {
		return jobBuilderFactory.get("producerJob")
				.incrementer(new RunIdIncrementer())
				.flow(createStep()).end().build();
	}

	
	@Bean
	public Step createStep() {
		return stepBuilderFactory.get("producerJobStep")
				.<Category, Category> chunk(100)
				.reader(reader())
				.writer(kafkaWriter)
				.taskExecutor(taskExecutor())
				//.faultTolerant()
				//.skipLimit(1)
				//.skip(FlatFileParseException.class)
				//.listener(new ItemChunkListener())
				.build();
	}
	
	@Bean
	public FlatFileItemReader<Category> reader() {
	  return new FlatFileItemReaderBuilder<Category>()
	    .name("categoryItemReader")
	    .resource(new ClassPathResource(FILENAME))
	    .linesToSkip(1)
	    .delimited()
	    .names(new String[]{"category", "value"})
	    .fieldSetMapper(new BeanWrapperFieldSetMapper<Category>() {{
	      setTargetType(Category.class);
	    }})
	    .build();
	}
	
	@Bean
	public TaskExecutor taskExecutor() {
		SimpleAsyncTaskExecutor taskExecutor = new 	SimpleAsyncTaskExecutor();
		taskExecutor.setConcurrencyLimit(5);
		return taskExecutor;
	}

}
