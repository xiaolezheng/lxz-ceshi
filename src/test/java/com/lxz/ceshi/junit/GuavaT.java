/*
 * Copyright (c) 2014 . All Rights Reserved.
 */
package com.lxz.ceshi.junit;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Iterators;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.PeekingIterator;

/**
 * @author: xiaole Date: 14-2-22 Time: 下午11:04
 * @version: \$Id$
 */
public class GuavaT {
    private static final Logger logger = LoggerFactory.getLogger(GuavaT.class);

    @org.junit.Test
    public void test() {
        List<Integer> result = Lists.newArrayList();
        List<Integer> list = Lists.newArrayList(1, 2, 3, 3, 4, 5, 3, 1);
        PeekingIterator<Integer> iter = Iterators.peekingIterator(list.iterator());
        while (iter.hasNext()) {
            Integer current = iter.next();
            while (iter.hasNext() && iter.peek().equals(current)) {
                // 跳过重复元素
                iter.next();
            }
            result.add(current);
        }
        logger.debug(result.toString());
    }

    @org.junit.Test
    public void multiSet() {
        List<Integer> list = Lists.newArrayList(2, 1, 3, 4, 5, 3, 2, 3, 3, 4, 4, 4, 4);
        Multiset<Integer> multiset = HashMultiset.create();
        for (Integer t : list) {
            multiset.add(t);
        }

        logger.debug("3 counter: {}", multiset.count(3));
    }

    @org.junit.Test
    public void multiMap() {
        List<String> list = Lists.newArrayList("1", "2", "3", "4", "2", "1");
        Multimap<String, String> map = ArrayListMultimap.create();
        for (String s : list) {
            map.put(s, s);
        }

        logger.debug(map.toString());

        ListMultimap<String, String> mapList = ArrayListMultimap.create();
        mapList.putAll("a", Lists.newArrayList("1", "2"));
        mapList.putAll("a", Lists.newArrayList("1", "2"));
        mapList.putAll("b", Lists.newArrayList("1", "2"));

        logger.debug(mapList.toString());

        logger.debug(mapList.containsKey("a") + "");
        logger.debug(mapList.get("a").toString());

        mapList.removeAll("a");

        logger.debug(mapList.toString());

        logger.debug(mapList.get("b").toString());

        logger.debug(mapList.containsKey("a") + "");
    }
}
