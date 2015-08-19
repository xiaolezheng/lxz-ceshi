/*
* Copyright (c) 2014 Qunar.com. All Rights Reserved.
*/
package com.lxz.guava;


import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.TimeUnit;

/**
 * @author: xiaole  Date: 15-5-24 Time: 上午11:51
 * @version: \$Id$
 */
public class Test {
    static RateLimiter limiter = RateLimiter.create(1000);

    public static void main(String[] args){

        long start = System.currentTimeMillis();
        for(int i=0; i<6;i++){
            log(i);
        }

        System.out.println(System.currentTimeMillis() - start);
    }


    public static void log(int msg){
        long start = System.currentTimeMillis();
        limiter.acquire();
        try{
            TimeUnit.MILLISECONDS.sleep(30);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("msg: " + msg + " cost: " + (System.currentTimeMillis() - start));
    }
}