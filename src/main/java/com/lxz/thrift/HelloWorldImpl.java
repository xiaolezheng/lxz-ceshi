/*
* Copyright (c) 2014 . All Rights Reserved.
*/
package com.lxz.thrift;

import org.apache.thrift.TException;

/**
 * @author: xiaole  Date: 15-1-25 Time: 下午7:16
 * @version: \$Id$
 */
public class HelloWorldImpl implements HelloWorldService.Iface{
    public HelloWorldImpl(){}

    @Override
    public String sayHello(String username) throws TException {
        return String.format("Hi %s welcome to my blog",username);
    }
}