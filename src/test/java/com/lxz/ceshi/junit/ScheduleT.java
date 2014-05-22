/*
 * Copyright (c) 2014 Qunar.com. All Rights Reserved.
 */
package com.lxz.ceshi.junit;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: xiaole Date: 14-2-21 Time: 下午5:28
 * @version: \$Id$
 */
public class ScheduleT {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleT.class);

    public static void main(String[] args) throws Exception {

        ScheduledExecutorService schedule = Executors.newSingleThreadScheduledExecutor();
        schedule.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {
                    logger.debug("task ceshi");
                    TimeUnit.SECONDS.sleep(1);

                } catch (Exception e) {
                    logger.error("", e);
                }
            }
        }, 2, 2, TimeUnit.SECONDS);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                logger.debug("shutdown jvm");
            }
        });

        TimeUnit.SECONDS.sleep(4);

        System.exit(0);

    }
}
