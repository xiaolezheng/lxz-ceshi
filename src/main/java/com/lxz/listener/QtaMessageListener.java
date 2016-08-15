/*
* Copyright (c) 2014 . All Rights Reserved.
*/
package com.lxz.listener;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author: xiaole  Date: 14-8-17 Time: 下午10:33
 * @version: \$Id$
 */
public class QtaMessageListener extends Cn6Listener{
    private static String group = "qta";
    private static String subject = "ps";

    public QtaMessageListener(){
        super(group,subject);

    }

    @Override
    public int getMessageType() {
        return 0;
    }

    @Override
    public void process(Message message) {

    }

    @Override
    public void onSuccess(int type) {

    }

    @Override
    public void onFail(int type) {

    }

    @Override
    public void printMsg(Message msg){
        System.out.println("ceshi...............");
    }

    public static void main(String[] args){
        Message msg = SystemMessage.create();
        msg.put("name","jim");
        msg.put("age","25");

        QtaMessageListener listener = new QtaMessageListener();
        listener.onMessage(msg);
    }

    private static class SystemMessage implements  Message{
        private Map<String,String> msg = Maps.newConcurrentMap();
        private SystemMessage(){}

        public static Message create(){
            return new SystemMessage();
        }

        @Override
        public void put(String key, String value) {
            msg.put(key,value);
        }

        public String toString(){
            return msg.toString();
        }
    }
}