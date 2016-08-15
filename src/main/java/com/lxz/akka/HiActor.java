/*
* Copyright (c) 2014 . All Rights Reserved.
*/
package com.lxz.akka;

import akka.actor.UntypedActor;

/**
 * @author: xiaole  Date: 15-5-6 Time: 下午11:00
 * @version: \$Id$
 */
public class HiActor extends UntypedActor {
    @Override
    public void onReceive(Object message) throws Exception {
        System.out.println("Hi " + message + " from thread " + Thread.currentThread().getName());
    }

}