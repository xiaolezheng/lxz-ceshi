/*
* Copyright (c) 2014 Qunar.com. All Rights Reserved.
*/
package com.lxz.ceshi.junit;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author: xiaole  Date: 15-5-24 Time: 上午11:53
 * @version: \$Id$
 */
public class GuavaTest {
    @org.junit.Test
    public void testRateLimiter1() throws Exception{
        // QPS限流控制
        final int QPS = 15;
        RateLimiter rateLimiter = RateLimiter.create(QPS);
        Random random = new Random();

        for(int i=0; i<100; i++){
            long start = System.currentTimeMillis();
            rateLimiter.acquire();
            System.out.println(System.currentTimeMillis()-start);
            // 模拟执行任务
            TimeUnit.MILLISECONDS.sleep(random.nextInt(20));
        }
    }

    @org.junit.Test
    public void testRateLimiter2(){
        final int QPS = 15;
        RateLimiter limiter = RateLimiter.create(QPS);
        for(int i=0; i<100; i++){
            long start = System.currentTimeMillis();
            if(limiter.tryAcquire(80, TimeUnit.MILLISECONDS)){
                System.out.println(System.currentTimeMillis()-start);
            }
        }
    }
}