/*
* Copyright (c) 2014 Qunar.com. All Rights Reserved.
*/
package com.lxz.ceshi.junit;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Slf4jReporter;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.google.common.base.Joiner;

import java.util.concurrent.TimeUnit;

/**
 * @author: xiaole  Date: 15-4-2 Time: 下午7:27
 * @version: \$Id$
 */
public class MetricsTest {
    private static final Logger logger = LoggerFactory.getLogger(MetricsTest.class);

    private static final MetricRegistry register = new MetricRegistry();

    private static final Slf4jReporter reporterLogger = Slf4jReporter.forRegistry(register)
            .outputTo(LoggerFactory.getLogger(MetricsTest.class))
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .build();

    private static final ConsoleReporter reporter = ConsoleReporter.forRegistry(register)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .build();

    @BeforeClass
    public static void before(){
        reporter.start(1, TimeUnit.SECONDS);
        reporterLogger.start(1, TimeUnit.SECONDS);
    }

    @AfterClass
    public static void after(){
        reporter.report();
        reporter.close();

        reporterLogger.report();
        reporterLogger.close();
    }

    @org.junit.Test
    public void testMeter() throws Exception{
        Meter requests = register.meter(name("testMeter", "requests"));
        for(int i=0; i<100000; i++){
            requests.mark();
            TimeUnit.MILLISECONDS.sleep(2);
        }
    }

    private String name(String prefix, String key){
        return Joiner.on(".").join(prefix, key);
    }
}
