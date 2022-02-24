package com.shpp.p2p.cs.nsigov.assignment16;

import java.util.NoSuchElementException;

/**
 * The class tests all public methods of the class myQueue
 */
public class TestMyQueue {

    /**
     * An instance of the class to test
     */
    private final MyQueue<String> TEST = new MyQueue<>();

    /**
     * Method calling all tests
     */
    public void runTests() {
        System.out.println("Tests myQueue:");
        testAdd();
        testPoll();
        testPeek();
        testClear();
        testIsEmpty();
        System.out.println();
    }

    /**
     * The method tests the correctness of the method clear
     */
    private void testClear() {
        // test = [a, b, c]
        TEST.clear();
        if (TEST.size() != 0) {
            throw new RuntimeException("Test testClear FAILED");
        }
        TEST.clear(); // empty queue
        System.out.println("Test testClear passed");
    }

    /**
     * The method tests the correctness of the method isEmpty
     */
    private void testIsEmpty() {
        // test = []
        if (!TEST.isEmpty()) {
            throw new RuntimeException("Test testIsEmpty FAILED");
        }
        TEST.add("a");
        if (TEST.isEmpty()) {
            throw new RuntimeException("Test testIsEmpty FAILED");
        }
        TEST.clear();
        TEST.add(null);
        if (TEST.isEmpty()) {
            throw new RuntimeException("Test testIsEmpty FAILED");
        }
        System.out.println("Test testEmpty passed");
    }

    /**
     * The method tests the correctness of the method peek
     */
    private void testPeek() {
        // test = []
        try {
            TEST.peek();
        } catch (NoSuchElementException e) {
            TEST.add("a"); // [a]
        }
        if (!TEST.peek().equals("a")) {
            throw new RuntimeException("Test testPeek FAILED");
        }
        TEST.add("b"); // [a, b]
        if (!TEST.peek().equals("a")) {
            throw new RuntimeException("Test testPeek FAILED");
        }
        TEST.add("c"); // [a, b, c]
        if (!TEST.peek().equals("a")) {
            throw new RuntimeException("Test testPeek FAILED");
        }
        if (TEST.size() != 3) {
            throw new RuntimeException("Test testPeek FAILED");
        }
        TEST.clear();
        TEST.add(null); // [null]
        if (TEST.peek() != null) {
            throw new RuntimeException("Test testPeek FAILED");
        }
        TEST.clear();
        System.out.println("Test testPeek passed");
    }

    /**
     * The method tests the correctness of the method poll
     */
    private void testPoll() {
        // test = [a, b, c, null, null]
        if (TEST.size() != 5) {
            throw new RuntimeException("Test testPoll FAILED");
        }
        if (!TEST.poll().equals("a")) {
            throw new RuntimeException("Test testPoll FAILED");
        }
        if (!TEST.poll().equals("b")) {
            throw new RuntimeException("Test testPoll FAILED");
        }
        if (!TEST.poll().equals("c")) {
            throw new RuntimeException("Test testPoll FAILED");
        }
        if (TEST.poll() != null) { // poll null value
            throw new RuntimeException("Test testPoll FAILED");
        }
        if (TEST.size() != 1) {
            throw new RuntimeException("Test testPoll FAILED");
        }
        if (TEST.poll() != null) {
            throw new RuntimeException("Test testPoll FAILED");
        }
        // empty queue
        try {
            TEST.poll();
        } catch (NoSuchElementException e) {
            TEST.add("d");
        }
        if (!TEST.poll().equals("d")) {
            throw new RuntimeException("Test testPoll FAILED");
        }
        System.out.println("Test testPoll passed");
    }

    /**
     * The method tests the correctness of the method add
     */
    private void testAdd() {
        if (TEST.size() != 0) {
            throw new RuntimeException("Test testAdd FAILED");
        }
        TEST.add("a"); // [a]
        TEST.add("b"); // [a ,b]
        TEST.add("c"); // [a, b, c]
        if (TEST.size() != 3) {
            throw new RuntimeException("Test testAdd FAILED");
        }
        if (!TEST.toString().equals("[a, b, c]")) {
            throw new RuntimeException("Test testAdd FAILED");
        }
        if (TEST.size() != 3) {
            throw new RuntimeException("Test testAdd FAILED");
        }
        TEST.add(null); // add null
        TEST.add(null);
        if (TEST.size() != 5) {
            throw new RuntimeException("Test testAdd FAILED");
        }
        System.out.println("Test testAdd passed");
    }
}