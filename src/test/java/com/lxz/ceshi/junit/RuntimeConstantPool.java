/*
 * Copyright (c) 2014 . All Rights Reserved.
 */
package com.lxz.ceshi.junit;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * -verbose:gc -XX:+PrintGCDetails -XX:PermSize=10M -XX:MaxPermSize=10M
 * 
 * @author: xiaole Date: 14-5-28 Time: 下午12:55
 * @version: \$Id$
 */
public class RuntimeConstantPool {
    public static void main(String[] args) {
        List<String> lists = Lists.newArrayList();
        int i = 0;
        while (true) {
            lists.add(String.valueOf(i++).intern());
        }
    }
}
