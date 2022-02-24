package com.shpp.p2p.cs.nsigov.assignment16;

import java.util.NoSuchElementException;

/**
 * The class tests all public methods of the class myStack
 */
public class TestMyStack {
    /**
     * An instance of the class to test
     */
    private final MyStack<String> TEST = new MyStack<>();

    /**
     * Method calling all tests
     */
    public void runTests() {
        System.out.println("Tests myStack:");
        testPush();
        testPop();
        testIsEmpty();
        testClear();
        testPeek();
        System.out.println();
    }

    /**
     * The method tests the correctness of the method clear
     */
    private void testClear() {
        // test = []
        TEST.push("1");
        TEST.clear();
        if (TEST.size() != 0) {
            throw new RuntimeException("Test testClear FAILED");
        }
        TEST.clear(); // empty stack
        System.out.println("Test testClear passed");
    }

    /**
     * The method tests the correctness of the method peek
     */
    private void testPeek() {
        // test = []
        try {
            TEST.peek();
        } catch (NoSuchElementException e) {
            TEST.push("a"); // [a]
        }
        if (!TEST.peek().equals("a")) {
            throw new RuntimeException("Test testPeek FAILED");
        }
        TEST.push("b"); // [a, b]
        if (!TEST.peek().equals("b")) {
            throw new RuntimeException("Test testPeek FAILED");
        }
        TEST.push("c"); // [a, b, c]
        if (!TEST.peek().equals("c")) {
            throw new RuntimeException("Test testPeek FAILED");
        }
        TEST.push(null);
        if (TEST.peek() != null) {
            throw new RuntimeException("Test testPeek FAILED");
        }
        if (TEST.size() != 4) {
            throw new RuntimeException("Test testPeek FAILED");
        }
        System.out.println("Test testPeek passed");
    }

    /**
     * The method tests the correctness of the method isEmpty
     */
    private void testIsEmpty() {
        // test = []
        if (!TEST.isEmpty()) {
            throw new RuntimeException("Test testIsEmpty FAILED");
        }
        TEST.push("a");
        if (TEST.isEmpty()) {
            throw new RuntimeException("Test testIsEmpty FAILED");
        }
        TEST.clear();
        TEST.push(null);
        if (TEST.isEmpty()) {
            throw new RuntimeException("Test testIsEmpty FAILED");
        }
        TEST.clear();
        System.out.println("Test testEmpty passed");
    }

    /**
     * The method tests the correctness of the method pop
     */
    private void testPop() {
        // test = [a, b, c, null, null]
        if (TEST.size() != 5) {
            throw new RuntimeException("Test testPop FAILED");
        }
        if (TEST.pop() != null) {
            throw new RuntimeException("Test testPop FAILED");
        }
        if (TEST.pop() != null) {
            throw new RuntimeException("Test testPop FAILED");
        }
        if (!TEST.pop().equals("c")) {
            throw new RuntimeException("Test testPop FAILED");
        }
        if (!TEST.pop().equals("b")) {
            throw new RuntimeException("Test testPop FAILED");
        }
        if (!TEST.pop().equals("a")) {
            throw new RuntimeException("Test testPop FAILED");
        }

        if (TEST.size() != 0) {
            throw new RuntimeException("Test testPop FAILED");
        }
        // empty stack
        try {
            TEST.pop();
        } catch (NoSuchElementException e) {
            TEST.push("d");
        }
        if (!TEST.pop().equals("d")) {
            throw new RuntimeException("Test testPop FAILED");
        }
        System.out.println("Test testPop passed");
    }

    /**
     * The method tests the correctness of the method push
     */
    private void testPush() {
        // test = []
        if (TEST.size() != 0) {
            throw new RuntimeException("Test testPush FAILED");
        }
        TEST.push("a"); // [a]
        TEST.push("b"); // [a ,b]
        TEST.push("c"); // [a, b, c]
        if (TEST.size() != 3) {
            throw new RuntimeException("Test testPush FAILED");
        }
        if (!TEST.toString().equals("[a, b, c]")) {
            throw new RuntimeException("Test testPush FAILED");
        }
        if (TEST.size() != 3) {
            throw new RuntimeException("Test testPush FAILED");
        }
        TEST.push(null); //  [a, b, c, null]
        TEST.push(null); //  [a, b, c, null, null]
        System.out.println("Test testPush passed");
    }
}

