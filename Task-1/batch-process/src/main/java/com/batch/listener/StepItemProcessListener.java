package com.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemProcessListener;

public class StepItemProcessListener implements ItemProcessListener<String, Number> {

	private Logger logger = LoggerFactory.getLogger(StepItemProcessListener.class);
	
	@Override
	public void beforeProcess(String item) {
		logger.debug("ItemProcessListener - beforeProcess");
	}

	@Override
	public void afterProcess(String item, Number result) {
		logger.debug("ItemProcessListener - afterProcess");
	}

	@Override
	public void onProcessError(String item, Exception e) {
		logger.debug("ItemProcessListener - onProcessError");
	}

}
