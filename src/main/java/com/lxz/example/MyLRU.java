package com.lxz.example;

import com.lxz.util.JsonUtil;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by xiaolezheng on 17/9/25.
 */
public class MyLRU {
    public static void main(String[] args){
        LRUCache<String,Integer> cache = new LRUCache<>(3);
        cache.put("a",1);
        cache.put("b",2);
        cache.put("c",3);

        System.out.println(cache.get("a"));
        cache.put("d",4);
        System.out.print(JsonUtil.encode(cache));
    }


    static class LRUCache<K, V> extends LinkedHashMap<K, V> {
        private int maxEntries;

        public LRUCache(int maxEntries){
            super(16, 0.75f, true);
            this.maxEntries = maxEntries;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return size() > maxEntries;
        }
    }
}
