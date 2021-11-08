package com.batch.listener;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;

public class StepItemWriteListener implements ItemWriteListener<Number> {

	private Logger logger = LoggerFactory.getLogger(StepItemWriteListener.class);
	
	@Override
	public void beforeWrite(List items) {
		logger.debug("ItemWriteListener - beforeWrite");
	}

	@Override
	public void afterWrite(List items) {
		logger.debug("ItemWriteListener - afterWrite");
	}

	@Override
	public void onWriteError(Exception exception, List items) {
		logger.debug("ItemWriteListener - onWriteError");
	}

}
