package com.lxz.example;

import lombok.Data;

/**
 * Created by xiaolezheng on 17/9/25.
 */
public class MyHashMap {
    public static void main(String[] args) {
        MyHashMap map = new MyHashMap();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);

        System.out.println(map.get("a"));
        System.out.println(map.get("b"));
    }


    private static final int SIZE = 16;
    private Entry[] table = new Entry[SIZE];

    public void put(String key, Object value) {
        int hash = key.hashCode() % SIZE;
        Entry e = table[hash];
        if (e != null) {
            while (e.next != null) {
                if (e.key.equals(key)) {
                    e.value = value;
                    return;
                }

                e = e.next;
            }

            Entry tail = new Entry(key, value);
            e.next = tail;
        } else {
            Entry head = new Entry(key, value);
            table[hash] = head;
        }
    }

    public Entry get(String key) {
        int hash = key.hashCode() % SIZE;
        Entry entry = table[hash];
        while (entry != null) {
            if (entry.key.equals(key)) {
                return entry;
            }

            entry = entry.next;
        }

        return null;
    }

    @Data
    class Entry {
        public Entry(String key, Object value) {
            this.key = key;
            this.value = value;
        }

        String key;
        Object value;
        Entry next;
    }
}