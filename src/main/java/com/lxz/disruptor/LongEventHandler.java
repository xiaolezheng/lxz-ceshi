package com.lxz.disruptor;

import com.lmax.disruptor.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class LongEventHandler implements EventHandler<LongEvent> {
    private static final Logger logger = LoggerFactory.getLogger(LongEventHandler.class);

    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) {
       logger.debug("Event: " + event+" | sequence: "+sequence);
        /*try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }
}
