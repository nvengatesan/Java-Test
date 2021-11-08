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

import com.batch.model.Category;

@Component
public class CategoryWriter extends JdbcBatchItemWriter<Category> implements ItemWriter<Category> {
	
	private static final Logger LOGGER =  LoggerFactory.getLogger(CategoryWriter.class);
	private static final String UPDATE_CATEGORY = "update category set "+
			 "status= :status where id = :id";
	
	public CategoryWriter(@Autowired DataSource dataSource) {
		LOGGER.info("Write category");
		
		
		setDataSource(dataSource);
		setSql(UPDATE_CATEGORY);
		
		ItemSqlParameterSourceProvider<Category> paramProvider = 
                new BeanPropertyItemSqlParameterSourceProvider<>();
        setItemSqlParameterSourceProvider(paramProvider);
        
	
	}
	
	


}
