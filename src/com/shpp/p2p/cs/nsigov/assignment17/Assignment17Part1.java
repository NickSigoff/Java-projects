package com.shpp.p2p.cs.nsigov.assignment17;

/**
 * Class Assignment 17 calls methods these launch the tests which check correct work of classes MyHashMap and MyPriorityQueue
 */

public class Assignment17Part1 {
    public static void main(String[] args) {
        TestMyHashMap testHashMap = new TestMyHashMap();
        TestMyPriorityQueue testPriorityQueue = new TestMyPriorityQueue();
        testHashMap.runTests();
        testPriorityQueue.runTests();
    }
}

