/*
* Copyright (c) 2014 . All Rights Reserved.
*/
package com.lxz.data;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author: xiaole  Date: 15-5-27 Time: 上午9:58
 * @version: \$Id$
 */
public class LruCache {
    private LinkedList<Integer> cache = new LinkedList();
    private int capacity;
    private HashMap<Integer, Integer> map = new HashMap();

    public LruCache(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        if (map.containsKey(key)) {
            int val = map.get(key);
            cache.removeFirstOccurrence(key);
            cache.addFirst(key);
            return val;
        } else
            return -1;
    }

    public void set(int key, int value) {
        if (map.containsKey(key)) {
            cache.removeFirstOccurrence(key);
            cache.addFirst(key);
        } else {
            if (cache.size() == capacity) {
                int lastKey = cache.removeLast();
                map.remove(lastKey);
            }
            cache.push(key);
            map.put(key, value);
        }
    }

    public static void main(String[] args){
        LruCache cache = new LruCache(3);
        cache.set(1, 2);
        cache.set(5, 3);
        cache.set(4, 4);

        System.out.println(cache.get(4));
        cache.set(6,6);

        System.out.print(cache.get(1));

    }

}