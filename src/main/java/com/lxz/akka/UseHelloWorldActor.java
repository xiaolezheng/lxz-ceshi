/*
* Copyright (c) 2014 Qunar.com. All Rights Reserved.
*/
package com.lxz.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * @author: xiaole  Date: 15-5-3 Time: 下午9:42
 * @version: \$Id$
 */
public class UseHelloWorldActor{
    public static void main(String[] args) {
        UseHelloWorldActor actor = new UseHelloWorldActor();
        actor.sendMsg("111");
        actor.sendMsg("222");
        actor.sendMsg("333");
        actor.sendMsg("444");
        actor.sendMsg("555");
    }



    public void sendMsg(String msg){
        ActorSystem system = ActorSystem.create("Hello");
        ActorRef a = system.actorOf(Props.create(HelloWorldActor.class));
        ActorRef b = system.actorOf(Props.create(HiActor.class));
        a.tell(msg, b);

        system.shutdown();
    }


}