/*
* Copyright (c) 2014 Qunar.com. All Rights Reserved.
*/
package com.lxz.listener;

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
}