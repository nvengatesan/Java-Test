package com.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DeadlockLoserDataAccessException;

import com.batch.core.CategoryProcessor;
import com.batch.core.CategoryReader;
import com.batch.core.CompositeCustomWriter;
import com.batch.listener.ItemChunkListener;
import com.batch.listener.SpringBatchJobCompletionListener;
import com.batch.listener.StepItemProcessListener;
import com.batch.listener.StepItemWriteListener;
import com.batch.listener.StepSkipListener;
import com.batch.model.Category;
import com.batch.model.SummaryCategories;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private CategoryReader categoryReader;
	
	@Autowired
	private CompositeCustomWriter compositeCustomWriter;

	@Autowired
	private CategoryProcessor categoryProcessor;
	
	@Bean
	public Job createCategoryJob() {
		return jobBuilderFactory.get("batchProcessJob")
				.incrementer(new RunIdIncrementer())
				.listener(new SpringBatchJobCompletionListener())
				.flow(createStep()).end().build();
	}

	
	@Bean
	public Step createStep() {
		return stepBuilderFactory.get("batchProcessStep")
				.<Category, SummaryCategories> chunk(100)
				.reader(categoryReader)
				.processor(categoryProcessor)
				.writer(compositeCustomWriter)
				.faultTolerant()
				.skipLimit(3)
				.skip(DeadlockLoserDataAccessException.class)
				.listener(new ItemChunkListener())
				.listener(new StepItemProcessListener())
				.listener(new StepItemWriteListener())
				.listener(new StepSkipListener())
				.build();
	}

	
	
}
