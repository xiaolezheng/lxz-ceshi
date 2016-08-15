/*
 * Copyright (c) 2014 . All Rights Reserved.
 */
package com.lxz.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Range;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @author: xiaole Date: 15-1-28 Time: 下午4:18
 * @version: \$Id$
 */
public final class LoggerHelper {
    private static final Logger logger = LoggerFactory.getLogger(LoggerHelper.class);
    private final Map<Integer, SystemClock> clockPool = Maps.newConcurrentMap();
    private final List<Integer> clockTime = Lists.newArrayList(1, 2, 5, 10, 20, 30, 60);
    private static final LoggerHelper instance = new LoggerHelper();

    private LoggerHelper() {
        for (Integer time : clockTime) {
            clockPool.put(time, new SystemClock(time));
        }
    }

    private static LoggerHelper getInstance(){
        return instance;
    }

    public void info(int time, Logger logger, String msg) {
        print(5,time,logger,msg);
    }

    public void warn(int time, Logger logger, String msg) {
        print(4,time,logger,msg);
    }

    private void print(int level, int time, Logger logger, String msg){
        SystemClock clock = clockPool.get(time);
        if (clock != null) {
            long lastTime = clock.now();
            while (true) {
                if (lastTime != clock.now()) {
                    if(level == 4) {
                        logger.warn(msg);
                    }else {
                        logger.info(msg);
                    }
                    lastTime = clock.now();
                }
            }
        }
    }


    public static void main(String[] args) {
        /*while (true){
            //LoggerHelper.getInstance().info(2, logger, ".........................");
            LoggerHelper.getInstance().warn(2, logger, ".........................");
        }*/
        while (true){
            long time = System.currentTimeMillis();
            if(time % (1000*2)==0){
                System.out.println("...................");
            }
        }
    }
}
