/*
* Copyright (c) 2014 Qunar.com. All Rights Reserved.
*/
package com.lxz.ceshi.junit;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
 * @author: xiaole  Date: 14-5-28 Time: 上午11:44
 * @version: \$Id$
 */


public class HeapOOM {
    static class OOMObject{}
    public static void main(String[] args){
        List<OOMObject> lists = Lists.newArrayList();
        while(true){
            lists.add(new OOMObject());
        }
    }
}