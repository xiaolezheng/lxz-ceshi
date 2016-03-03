/*
* Copyright (c) 2014 Qunar.com. All Rights Reserved.
*/
package com.lxz.diff;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.danielbechler.diff.ObjectDifferBuilder;
import de.danielbechler.diff.node.DiffNode;
import de.danielbechler.diff.selector.ElementSelector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author: xiaole  Date: 15-1-27 Time: 下午9:39
 * @version: \$Id$
 */
public class DiffTest {
    private static final Logger logger = LoggerFactory.getLogger(DiffTest.class);
    public static void main(String[] args){
        Map<String,Object> workMap = Maps.newHashMap();
        workMap.put("age",10);
        workMap.put("name", "jim");
        workMap.put("address", Maps.newHashMap(ImmutableMap.of("code",122,"gps",11)));

        Map<String,Object> baseMap = Maps.newHashMap();
        baseMap.put("age",12);
        baseMap.put("name", "jimd");

        DiffNode root = ObjectDifferBuilder.buildDefault().compare(workMap, baseMap);



        logger.info("DiffTest, result: {}",root);

        //log.info("DiffTest, result: {}",root);
    }
}