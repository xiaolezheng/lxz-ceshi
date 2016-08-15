/*
 * Copyright (c) 2014 . All Rights Reserved.
 */
package com.lxz.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: xiaole Date: 14-7-4 Time: 上午11:00
 * @version: \$Id$
 */
public class TimingThreadPool extends ThreadPoolExecutor {
    private static final Logger logger = LoggerFactory.getLogger(TimingThreadPool.class);
    private final ThreadLocal<Long> startTime = new ThreadLocal<Long>();
    private final AtomicLong runTasks = new AtomicLong();
    private final AtomicLong totalTime = new AtomicLong();

    public TimingThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
            BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    public void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        logger.debug("thread: {}, runnable: {}", t, r);
        startTime.set(System.nanoTime());
    }

    @Override
    public void afterExecute(Runnable r, Throwable t) {
        try {
            long endTime = System.nanoTime();
            long taskTime = endTime - startTime.get();
            runTasks.incrementAndGet();
            totalTime.addAndGet(taskTime);
            logger.debug("thread: {}, time: {}", r, taskTime);
        } finally {
            super.afterExecute(r, t);
            startTime.remove();
        }
    }

    @Override
    public void terminated() {
        try {
            logger.debug("avg time: {}", totalTime.get() / runTasks.get());
        } finally {
            super.terminated();
        }
    }


    public static void main(String[] args){
        TimingThreadPool timingThreadPool = new TimingThreadPool(5,5,5,TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(10));
        for(int i=0; i<10; i++){
            timingThreadPool.submit(new Thread(){
                public void run(){
                    try{
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (Exception e){

                    }
                    logger.debug("thread:{} running",Thread.currentThread());
                }
            });
        }

        timingThreadPool.shutdown();
    }
}
