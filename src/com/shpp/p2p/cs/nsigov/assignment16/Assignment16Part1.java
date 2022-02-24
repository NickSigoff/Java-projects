package com.shpp.p2p.cs.nsigov.assignment16;

/**
 * Objects of testers classes are created in the class
 */
public class Assignment16Part1 {
    public static void main(String[] args) {
        TestMyLinkedList testLinkedList = new TestMyLinkedList();
        TestMyQueue testQueue = new TestMyQueue();
        TestMyStack testStack = new TestMyStack();
        TestMyArrayList testArrayList = new TestMyArrayList();

        testLinkedList.runTests();
        testQueue.runTests();
        testStack.runTests();
        testArrayList.runTests();
    }
}
