package com.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
 

public class ItemChunkListener implements ChunkListener {
    
   private static final Logger LOGGER  = LoggerFactory.getLogger(ItemChunkListener.class);
   
   @Override
   public void beforeChunk(ChunkContext context) {
   }

   @Override
   public void afterChunk(ChunkContext context) {
        
       int count = context.getStepContext().getStepExecution().getReadCount();
       LOGGER.info("ItemCount: " + count);
   }
    
   @Override
   public void afterChunkError(ChunkContext context) {
	   LOGGER.error("after Chunk Error");
   }
}
