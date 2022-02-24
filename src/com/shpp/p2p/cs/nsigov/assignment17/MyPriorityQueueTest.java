package com.shpp.p2p.cs.nsigov.assignment17;

import org.junit.jupiter.api.Test;

import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.*;

class MyPriorityQueueTest {
    MyPriorityQueue<Integer> test = new MyPriorityQueue<>();
    PriorityQueue<Integer> origin = new PriorityQueue<>();

    @Test
    void add() {
        for (int i = 0; i < 100; i++) {
            test.add(i);
            origin.add(i);
        }
        assertEquals(test.size(), origin.size());
    }

    @Test
    void poll() {
        add();
        while(test.peek() != null) {
            assertEquals(test.poll(), origin.poll());
        }
    }

    @Test
    void clear() {
        add();
        test.clear();
        origin.clear();
        assertEquals(test.size(), origin.size());
    }
}