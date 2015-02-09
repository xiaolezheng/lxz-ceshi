package com.lxz.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * {@link SystemClock} is a optimized substitute of {@link java.lang.System} for avoiding context switch overload.
 * <p/>
 * Every instance would start a thread to update the time, so it's supposed to be singleton in application context.
 * 
 */
public class SystemClock {
    private static final Logger logger = LoggerFactory.getLogger(SystemClock.class);

    private final long precision;
    private final AtomicLong now;

    public SystemClock(long precision) {
        this.precision = precision * 1000;
        now = new AtomicLong(System.currentTimeMillis());
        scheduleClockUpdating();
    }

    private void scheduleClockUpdating() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable, "System Clock");
                thread.setDaemon(true);
                return thread;
            }
        });
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                now.set(System.currentTimeMillis());
            }
        }, precision, precision, TimeUnit.MILLISECONDS);
    }

    public long now() {
        return now.get();
    }

    public long precision() {
        return precision;
    }

    public static void main(String[] args){
        SystemClock clock = new SystemClock(5000);
        long lastTime = clock.now();
        while (true){
            if(clock.now() != lastTime ){
                logger.info(".....................................");
                lastTime = clock.now();
            }
        }
    }
}
