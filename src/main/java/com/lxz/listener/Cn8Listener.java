/*
* Copyright (c) 2014 Qunar.com. All Rights Reserved.
*/
package com.lxz.listener;

/**
 * @author: xiaole  Date: 14-8-17 Time: 下午10:22
 * @version: \$Id$
 */
public abstract class Cn8Listener extends ScmMessageListner{
    private String cn8Consumer;

    public Cn8Listener(String group,String subject){
        super(group,subject);
    }

}