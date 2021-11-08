package com.batch.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.batch.model.Category;
import com.batch.model.SummaryCategories;

@Component
public class CategoryProcessor implements ItemProcessor<Category, SummaryCategories> {

	private static final Logger LOGGER =  LoggerFactory.getLogger(CategoryProcessor.class);
	
	@Override
	public SummaryCategories process(final Category category) throws Exception {
		final SummaryCategories summaryCategories = new SummaryCategories();
		summaryCategories.setCategories(category.getCategories());
		summaryCategories.setCategoriesId(category.getId());
		
        LOGGER.info("Processed: " + summaryCategories.getCategories());
        
		return summaryCategories;
	}

}
