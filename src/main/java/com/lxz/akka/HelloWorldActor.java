/*
* Copyright (c) 2014 Qunar.com. All Rights Reserved.
*/
package com.lxz.akka;

import akka.actor.UntypedActor;

/**
 * @author: xiaole  Date: 15-5-3 Time: 下午9:40
 * @version: \$Id$
 */
public class HelloWorldActor extends UntypedActor {
    @Override
    public void onReceive(Object message) throws Exception {
        System.out.println("Hello "+message+" from thread "+Thread.currentThread().getName());
        getSelf().tell(message, getSender());
    }
}