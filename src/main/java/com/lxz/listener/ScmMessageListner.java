/*
* Copyright (c) 2014 Qunar.com. All Rights Reserved.
*/
package com.lxz.listener;

/**
 * @author: xiaole  Date: 14-8-17 Time: 下午10:29
 * @version: \$Id$
 */
public abstract class ScmMessageListner implements MessageListener{
    private String group;
    private String subject;

    public ScmMessageListner(String group,String subject){
        this.group = group;
        this.subject = subject;
    }

    @Override
    public void onMessage(Message message){
        int type = getMessageType();
        try{
            process(message);

            onSuccess(type);
        }catch (Exception e){
            onFail(type);
            e.printStackTrace();
        }
    }

    public abstract int getMessageType();

    public abstract void process(Message message);

    public abstract void onSuccess(int type);

    public abstract void onFail(int type);
}