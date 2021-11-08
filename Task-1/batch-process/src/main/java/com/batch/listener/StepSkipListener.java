package com.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;


public class StepSkipListener implements SkipListener<String, Number> {

	private Logger logger = LoggerFactory.getLogger(StepSkipListener.class);
	
	@Override
	public void onSkipInRead(Throwable t) {
		logger.debug("StepSkipListener - onSkipInRead");
	}

	@Override
	public void onSkipInWrite(Number item, Throwable t) {
		logger.debug("StepSkipListener - afterWrite");
	}

	@Override
	public void onSkipInProcess(String item, Throwable t) {
		logger.debug("StepSkipListener - onWriteError");
	}

}
