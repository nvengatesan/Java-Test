package com.batch.core;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.batch.model.Summary;

@Component
public class SummaryWriter extends JdbcBatchItemWriter<Summary>  implements ItemWriter<Summary> {
	
	private static final Logger LOGGER =  LoggerFactory.getLogger(SummaryWriter.class);
	private static final String INSERT_SUMMARY = "insert into summary(category_summary) values (:categorySummary) ";
	
	public SummaryWriter(@Autowired DataSource dataSource) {
		LOGGER.info("Write summary");
		
		setDataSource(dataSource);
		setSql(INSERT_SUMMARY);
		
		
		ItemSqlParameterSourceProvider<Summary> paramProvider = 
               new BeanPropertyItemSqlParameterSourceProvider<>();
       setItemSqlParameterSourceProvider(paramProvider);
       
	
	}
}
