package com.shpp.p2p.cs.nsigov.assignment17;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class MyHashMapTest {
    MyHashMap<String, String> test = new MyHashMap<>();
    HashMap<String, String> origin = new HashMap<>();

    @org.junit.jupiter.api.Test
    void put() {
        for (int i = 0; i < 100; i++) {
            test.put(String.valueOf(i), String.valueOf(i + i));
            origin.put(String.valueOf(i), String.valueOf(i + i));
        }
        assertEquals(test.size(), origin.size());
    }

    @org.junit.jupiter.api.Test
    void get() {
        put();
        for (int i = 0; i < 100; i++) {
            assertEquals(test.get(String.valueOf(i)), origin.get(String.valueOf(i)));
        }
    }

    @org.junit.jupiter.api.Test
    void containsKey() {
        put();
        for (int i = 0; i < 200; i++) {
            assertEquals(test.containsKey(String.valueOf(i)), origin.containsKey(String.valueOf(i)));
        }
    }

    @org.junit.jupiter.api.Test
    void containsValue() {
        put();
        for (int i = 0; i < 400; i++) {
            assertEquals(test.containsValue(String.valueOf(i)), origin.containsValue(String.valueOf(i)));
        }
    }

    @org.junit.jupiter.api.Test
    void clear() {
        put();
        test.clear();
        origin.clear();
        assertEquals(test.size(), origin.size());
    }

    @org.junit.jupiter.api.Test
    void remove() {
        put();
        for (int i = 0; i < 50; i++) {
            test.remove(String.valueOf(i));
            origin.remove(String.valueOf(i));
        }
        assertEquals(test.size(), origin.size());
        for (int i = 0; i < 100; i++) {
            assertEquals(test.get(String.valueOf(i)), origin.get(String.valueOf(i)));
        }
        assertEquals(test.size(), origin.size());
    }
}