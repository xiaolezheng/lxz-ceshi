package com.lxz.disruptor;

import com.lmax.disruptor.EventHandler;

import java.util.concurrent.TimeUnit;

public class LongEventHandler implements EventHandler<LongEvent> {
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) {
       System.out.println("Event: " + event+" | sequence: "+sequence);
        try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
