package com.shpp.p2p.cs.nsigov.assignment17;

public class TestMyPriorityQueue {

    /**
     * An instance of the class to test
     */
    private final MyPriorityQueue<Integer> TEST = new MyPriorityQueue<>();

    /**
     * Method calls all test functions
     */
    public void runTests() {
        System.out.println("Tests MyPriorityQueue:");
        testAdd();
        testPoll();
        testPeek();
        testClear();
        testIsEmpty();
        System.out.println();
    }

    /**
     * Method tests correct work of the clear method
     */
    private void testClear() {
        // test = [1, 2, 3]
        if (TEST.size() != 3) {
            throw new RuntimeException("Test testClear FAILED");
        }
        TEST.clear();
        if (TEST.size() != 0) {
            throw new RuntimeException("Test testClear FAILED");
        }
        TEST.clear(); // empty queue
        System.out.println("Test testClear passed");
    }

    /**
     * Method tests correct work of the isEmpty method
     */
    private void testIsEmpty() {
        // test = []
        if (!TEST.isEmpty()) {
            throw new RuntimeException("Test testIsEmpty FAILED");
        }
        TEST.add(1);
        if (TEST.isEmpty()) {
            throw new RuntimeException("Test testIsEmpty FAILED");
        }
        System.out.println("Test testEmpty passed");
    }

    /**
     * Method tests correct work of the peek method
     */
    private void testPeek() {
        // test = []
        if (TEST.peek() != null) { // empty queue
            throw new RuntimeException("Test testPeek FAILED");
        }
        TEST.add(2);
        if (!TEST.peek().equals(2)) {
            throw new RuntimeException("Test testPeek FAILED");
        }
        TEST.add(3); // [2, 3]
        if (!TEST.peek().equals(2)) {
            throw new RuntimeException("Test testPeek FAILED");
        }
        TEST.add(1); // [1, 2, 3]
        if (!TEST.peek().equals(1)) {
            throw new RuntimeException("Test testPeek FAILED");
        }
        if (TEST.size() != 3) {
            throw new RuntimeException("Test testPeek FAILED");
        }
        System.out.println("Test testPeek passed");
    }

    /**
     * Method tests correct work of the poll method
     */
    private void testPoll() {
        // [1, 2, 3, 4, 5, 11]
        if (TEST.size() != 6) {
            throw new RuntimeException("Test testPoll FAILED");
        }
        if (!TEST.poll().equals(1)) {
            throw new RuntimeException("Test testPoll FAILED");
        }
        if (!TEST.poll().equals(2)) {
            throw new RuntimeException("Test testPoll FAILED");
        }
        if (!TEST.poll().equals(3)) {
            throw new RuntimeException("Test testPoll FAILED");
        }
        if (!TEST.poll().equals(4)) {
            throw new RuntimeException("Test testPoll FAILED");
        }
        if (!TEST.poll().equals(5)) {
            throw new RuntimeException("Test testPoll FAILED");
        }
        if (!TEST.poll().equals(11)) {
            throw new RuntimeException("Test testPoll FAILED");
        }
        if (TEST.poll() != null) { // Empty queue
            throw new RuntimeException("Test testPoll FAILED");
        }
        if (TEST.size() != 0) {
            throw new RuntimeException("Test testPoll FAILED");
        }
        System.out.println("Test testPoll passed");
    }

    /**
     * Method tests correct work of the add method
     */
    private void testAdd() {
        if (TEST.size() != 0) {
            throw new RuntimeException("Test testAdd FAILED");
        }
        TEST.add(1); // [1]
        TEST.add(2); // [1 ,2]
        TEST.add(3); // [1, 2, 3]
        TEST.add(4); // [1, 2, 3, 4]
        TEST.add(5); // [1, 2, 3, 4, 5]
        if (TEST.size() != 5) {
            throw new RuntimeException("Test testAdd FAILED");
        }
        if (!TEST.toString().equals("[1, 2, 3, 4, 5]")) {
            throw new RuntimeException("Test testAdd FAILED");
        }
        try {
            TEST.add(null); // add null
        } catch (NullPointerException e) {
            TEST.add(11); // [1, 2, 3, 4, 5, 11]
        }
        if (TEST.size() != 6) {
            throw new RuntimeException("Test testAdd FAILED");
        }
        System.out.println("Test testAdd passed");
    }
}

