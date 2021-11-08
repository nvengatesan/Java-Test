package com.batch.core;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.batch.model.Category;
import com.batch.model.Summary;
import com.batch.model.SummaryCategories;

@Component
public class CompositeCustomWriter implements ItemWriter<SummaryCategories> {

	@Autowired
	private final SummaryWriter summaryWriter;
	@Autowired
	private final CategoryWriter categoryWriter;

	public CompositeCustomWriter(SummaryWriter summaryWriter,CategoryWriter categoryWriter) {
	    this.summaryWriter=summaryWriter;
	    this.categoryWriter=categoryWriter;
	}
	
	@Override
	public void write(List<? extends SummaryCategories> items) throws Exception {
		
		 List<Summary> summaryList = items.stream().map(it -> {
			 Summary summary = new Summary();
			 summary.setCategorySummary(it.getCategories());
			 return summary;
		 }).collect(Collectors.toList());
		
		 
		 List<Category> categoryList = items.stream().map(it -> {
			 Category category = new Category();
			 category.setId(it.getCategoriesId());
			 category.setStatus(0);
			 return category;
		 }).collect(Collectors.toList());

		 summaryWriter.write(summaryList);
		 categoryWriter.write(categoryList);
	}
}


