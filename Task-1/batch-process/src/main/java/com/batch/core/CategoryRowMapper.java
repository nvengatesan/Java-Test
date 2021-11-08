package com.batch.core;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.batch.model.Category;

public class CategoryRowMapper implements RowMapper<Category> {
	
	private static final String CATEGORIES = "categories";
	private static final String STATUS = "status";
	private static final String ID = "id";
	
	@Override
	public Category mapRow(final ResultSet rs, final int rowNum) throws SQLException {
		Category category  = new Category();
		category.setId(rs.getInt(ID));
		category.setCategories(rs.getString(CATEGORIES));
		category.setStatus(rs.getInt(STATUS));
		return category;
	}
}
