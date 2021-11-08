package com.batch.core;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.batch.model.Category;

@Component
public class CategoryReader extends JdbcCursorItemReader<Category> implements ItemReader<Category>{
	
	private static final Logger LOGGER  = LoggerFactory.getLogger(CategoryReader.class);
	private static final String CATEGORY_FETCH = "select id,categories,status from category where status = 1"; 
	
	public CategoryReader(@Autowired DataSource dataSource) {
		
		LOGGER.info("MyBatchProcessor : Reading data : ");
		
		setDataSource(dataSource);
		setSql(CATEGORY_FETCH);
		setFetchSize(100);
		
		setRowMapper(new CategoryRowMapper());
	}
}
