/*
* Copyright (c) 2014 Qunar.com. All Rights Reserved.
*/
package com.lxz.listener;

/**
 * @author: xiaole  Date: 14-8-17 Time: 下午10:22
 * @version: \$Id$
 */
public abstract class Cn6Listener extends ScmMessageListner{
    private String cn6Consumer;

    public Cn6Listener(String group,String subject){
        super(group,subject);
    }

}