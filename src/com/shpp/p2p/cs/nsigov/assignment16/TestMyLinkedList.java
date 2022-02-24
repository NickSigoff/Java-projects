package com.shpp.p2p.cs.nsigov.assignment16;

import java.util.NoSuchElementException;

/**
 * The class tests all public methods of the class myLinkedList
 */
public class TestMyLinkedList {
    /**
     * An instance of the class to test
     */
    private final MyLinkedList<Integer> TEST = new MyLinkedList<>();

    /**
     * Method calling all tests
     */
    public void runTests() {
        System.out.println("Tests myLinkedList:");
        testAdd();
        testAddIndex();
        testAddFirst();
        testAddLast();
        testContains();
        testSet();
        testRemove();
        testRemoveFirst();
        testRemoveLast();
        testGet();
        testGetFirst();
        testGetLast();
        testIndexOf();
        System.out.println();
    }

    /**
     * The method tests the correctness of the method IndexOf
     */
    private void testIndexOf() {
        // test = [555]
        if (TEST.indexOf(555) != 0) { // only one element
            throw new RuntimeException("Test testIndexOf FAILED");
        }
        TEST.addLast(55); // [555, 55]
        TEST.addLast(5); // [555, 55, 5]
        TEST.addFirst(55); // [55, 555, 55, 5]
        TEST.addFirst(5); // [5, 55, 555, 55, 5]
        if (TEST.indexOf(5555) != -1) {
            throw new RuntimeException("Test testIndexOf FAILED");
        }
        if (TEST.indexOf(5) != 0) {
            throw new RuntimeException("Test testIndexOf FAILED");
        }
        if (TEST.indexOf(55) != 1) {
            throw new RuntimeException("Test testIndexOf FAILED");
        }
        if (TEST.indexOf(555) != 2) {
            throw new RuntimeException("Test testIndexOf FAILED");
        }
        TEST.clear(); // empty List
        try {
            TEST.indexOf(555);
        } catch (NoSuchElementException e) {
            System.out.println("Test testIndexOf passed");
        }
        TEST.add(null); // null check
        TEST.add(null);
        TEST.addFirst(5);
        if (TEST.indexOf(null) != 1) {
            throw new RuntimeException("Test testIndexOf FAILED");
        }
    }

    /**
     * The method tests the correctness of the method Get
     */
    private void testGet() {
        // test = [] Empty list
        try {
            TEST.get(-1); // negative index
        } catch (IndexOutOfBoundsException e) {
            TEST.add(0); // [0]
        }
        try {
            TEST.get(4); // oversize index
        } catch (IndexOutOfBoundsException e) {
            TEST.add(1); // [0, 1]
        }
        if (TEST.size() != 2) {
            throw new RuntimeException("Test testGet FAILED");
        }
        // Not empty List
        for (int i = 0; i < 10; i++) { // [0, 1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
            TEST.add(i);
        }
        if (TEST.get(7) != 5) {
            throw new RuntimeException("Test testGet FAILED");
        }
        try {
            TEST.get(-1); // negative index
        } catch (IndexOutOfBoundsException e) {
            TEST.removeLast(); // [0, 1, 0, 1, 2, 3, 4, 5, 6, 7, 8]
        }
        try {
            TEST.get(20); // oversize index
        } catch (IndexOutOfBoundsException e) {
            TEST.removeFirst(); // [1, 0, 1, 2, 3, 4, 5, 6, 7, 8]
        }
        TEST.add(null); // [1, 0, 1, 2, 3, 4, 5, 6, 7, 8, null]
        if (TEST.get(10) != null) {
            throw new RuntimeException("Test testGet FAILED");
        }
        if (TEST.size() != 11) {
            throw new RuntimeException("Test testGet FAILED");
        }
        if (!TEST.toString().equals("[1, 0, 1, 2, 3, 4, 5, 6, 7, 8, null]")) {
            throw new RuntimeException("Test testGet FAILED");
        }
        TEST.removeLast();
        System.out.println("Test testGet passed");
    }

    /**
     * The method tests the correctness of the method GetLast
     */
    private void testGetLast() {
        // test = [5]
        if (TEST.getLast() != 5) { // only one element
            throw new RuntimeException("Test testGetLast FAILED");
        }
        TEST.add(55); // [5, 55]
        if (TEST.getLast() != 55) { // only one element
            throw new RuntimeException("Test testGetLast FAILED");
        }
        TEST.clear(); // []
        try { // empty list
            TEST.getLast();
        } catch (NoSuchElementException e) {
            TEST.add(555); // [555]
        }
        if (TEST.getLast() != 555) {
            throw new RuntimeException("Test testGetLast FAILED");
        }
        TEST.addLast(null); // [555, null]
        if (TEST.getLast() != null) {
            throw new RuntimeException("Test testGetLast FAILED");
        }
        TEST.removeLast();
        System.out.println("Test testGetLast passed");
    }

    /**
     * The method tests the correctness of the method GetFirst
     */
    private void testGetFirst() {
        // test = [1, 0, 1, 2, 3, 4, 5, 6, 7, 8]
        if (TEST.getFirst() != 1) {
            throw new RuntimeException("Test testGetFirst FAILED");
        }
        TEST.clear();
        try { // empty list
            TEST.getFirst();
        } catch (NoSuchElementException e) {
            TEST.add(5);
        }
        if (TEST.getFirst() != 5) { // only one element
            throw new RuntimeException("Test testGetFirst FAILED");
        }
        TEST.addFirst(null); // [null, 5]
        if (TEST.getFirst() != null) { // only one element
            throw new RuntimeException("Test testGetFirst FAILED");
        }
        TEST.removeFirst();
        System.out.println("Test testGetFirst passed");
    }

    /**
     * The method tests the correctness of the method removeLast
     */
    private void testRemoveLast() {
        // test = []
        try {
            TEST.removeLast(); // empty list
        } catch (NoSuchElementException e) {
            TEST.add(0); // [0]
            TEST.add(1); // [0 ,1]
        }
        TEST.removeLast(); // [0]
        if (TEST.size() != 1) {
            throw new RuntimeException("Test testRemoveLast FAILED");
        }
        if (!TEST.toString().equals("[0]")) {
            throw new RuntimeException("Test testRemoveLast FAILED");
        }
        TEST.addLast(null); // [0 ,null]
        TEST.removeLast();
        TEST.removeLast(); // only one element []
        if (TEST.size() != 0) {
            throw new RuntimeException("Test testRemoveLast FAILED");
        }
        System.out.println("Test testRemoveLast passed");
    }

    /**
     * The method tests the correctness of the method removeFirst
     */
    private void testRemoveFirst() {
        // test = []
        try {
            TEST.removeFirst(); // empty list
        } catch (NoSuchElementException e) {
            TEST.add(0); // [0]
            TEST.add(1); // [0 ,1]
        }
        TEST.removeFirst(); // [1]
        if (TEST.size() != 1) {
            throw new RuntimeException("Test testRemoveFirst FAILED");
        }
        if (!TEST.toString().equals("[1]")) {
            throw new RuntimeException("Test testRemoveFirst FAILED");
        }
        TEST.addFirst(null); // [null, 1]
        TEST.removeFirst(); // [1]
        TEST.removeFirst(); // only one element []
        if (TEST.size() != 0) {
            throw new RuntimeException("Test testRemoveFirst FAILED");
        }
        System.out.println("Test testRemoveFirst passed");
    }

    /**
     * The method tests the correctness of the method remove
     */
    private void testRemove() {
        // test = []
        int i = 6;
        TEST.add(i++); // [6]
        TEST.add(i++); // [6 ,7]
        TEST.add(i++); // [6, 7, 8]
        TEST.add(i); // [6, 7, 8 ,9]
        TEST.remove(3); // [6, 7, 8]
        try {
            TEST.remove(-1); // negative index
        } catch (IndexOutOfBoundsException e) {
            TEST.remove(0); // [7, 8]
        }
        try {
            TEST.remove(4); // oversize index
        } catch (IndexOutOfBoundsException e) {
            TEST.remove(1); // [7]
        }
        if (TEST.size() != 1) {
            throw new RuntimeException("Test testRemove FAILED");
        }
        if (!TEST.toString().equals("[7]")) {
            throw new RuntimeException("Test testRemove FAILED");
        }
        TEST.add(null); // check null element  [7, null]
        TEST.remove(1); // [7]
        TEST.remove(0); // only one element
        try {
            TEST.remove(0); // empty list
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Test testRemove passed");
        }
    }

    /**
     * The method tests the correctness of the method set
     */
    private void testSet() {
        // test = []
        int i = 5;
        TEST.add(i++); // [5]
        TEST.set(0, 55); // one element
        TEST.add(i++); // [55, 6]
        TEST.add(i++); // [55, 6, 7]
        if (!TEST.toString().equals("[55, 6, 7]")) {
            throw new RuntimeException("Test testSet FAILED");
        }
        TEST.set(0, i++); // [8, 6, 7]
        try {
            TEST.set(-1, 3); // negative index
        } catch (IndexOutOfBoundsException e) {
            TEST.set(1, i++); // [8, 9, 7]
        }
        try {
            TEST.set(4, 3); // oversize index
        } catch (IndexOutOfBoundsException e) {
            TEST.set(2, i++); // [8, 9, 10]
        }
        if (TEST.size() != 3) {
            throw new RuntimeException("Test testSet FAILED");
        }
        TEST.set(1, null); // null meaning [8, null, 10]
        if (!TEST.toString().equals("[8, null, 10]")) {
            throw new RuntimeException("Test testSet FAILED");
        }
        TEST.clear();
        try {
            TEST.set(0, i); // empty list
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Test testSet passed");
        }
    }

    /**
     * The method tests the correctness of the method contains
     */
    private void testContains() {
        // test = [12]
        if (!TEST.contains(12)) {
            throw new RuntimeException("Test testContains FAILED");
        }
        if (TEST.contains(2)) {
            throw new RuntimeException("Test testContains FAILED");
        }
        TEST.clear();
        if (TEST.contains(0)) { // empty list
            throw new RuntimeException("Test testContains FAILED");
        }
        TEST.add(null); // add null
        if (!TEST.contains(null)) { // if null Nodes are into the list
            throw new RuntimeException("Test testContains FAILED");
        }
        TEST.addFirst(null); // [null, null]
        if (!TEST.contains(null)) { // if 2 null Nodes are into the list
            throw new RuntimeException("Test testContains FAILED");
        }
        TEST.clear();
        if (TEST.contains(null)) { // if null Nodes are into the list after clear
            throw new RuntimeException("Test testContains FAILED");
        }
        System.out.println("Test testContains passed");
    }

    /**
     * The method tests the correctness of the method addLast
     */
    private void testAddLast() {
        // test = [12]
        int i = 0;
        TEST.addLast(i++); // [12, 0]
        TEST.addLast(i); // [12, 0, 1]
        if (TEST.get(TEST.size() - 1) != 1) {
            throw new RuntimeException("Test testAddLast FAILED");
        }
        if (TEST.size() != 3) {
            throw new RuntimeException("Test testAddLast FAILED");
        }
        TEST.clear();
        TEST.addLast(12); // empty size [12]
        if (TEST.get(0) != 12) {
            throw new RuntimeException("Test testAddLast FAILED");
        }
        if (TEST.size() != 1) {
            throw new RuntimeException("Test testAddLast FAILED");
        }
        System.out.println("Test testAddLast passed");
    }

    /**
     * The method tests the correctness of the method addFirst
     */
    private void testAddFirst() {
        // test = [4, 0, null, 1, 2, 3]
        int i = 10;
        TEST.addFirst(null);
        TEST.addFirst(i++); // [10, null, 4, 0, null, 1, 2, 3]
        TEST.addFirst(i++); // [11, 10, null, 4, 0, null, 1, 2, 3]
        if (TEST.get(0) != 11) {
            throw new RuntimeException("Test testAddFirst FAILED");
        }
        if (TEST.get(1) != 10) {
            throw new RuntimeException("Test testAddFirst FAILED");
        }
        TEST.clear();
        TEST.addFirst(i); // empty size [12]
        if (TEST.get(0) != 12) {
            throw new RuntimeException("Test testAddFirst FAILED");
        }
        if (TEST.size() != 1) {
            throw new RuntimeException("Test testAddFirst FAILED");
        }
        System.out.println("Test testAddFirst passed");
    }

    /**
     * The method tests the correctness of the method addIndex
     */
    private void testAddIndex() {
        int i = 0;
        TEST.clear();
        TEST.add(i, i++); // [0]
        TEST.add(i, i++); // [0, 1]
        try {
            TEST.add(-1, 2); // negative index
        } catch (IndexOutOfBoundsException e) {
            TEST.add(i, i++); // [0, 1, 2]
        }
        try {
            TEST.add(10, 4); // oversize index
        } catch (IndexOutOfBoundsException e) {
            TEST.add(i, i++); // [0, 1, 2, 3]
        }
        TEST.add(0, i); // occupied index [4, 0, 1, 2, 3]
        TEST.add(2, null); // [4, 0, null, 1, 2, 3]
        if (TEST.size() != 6) {
            throw new RuntimeException("Test testAddIndex FAILED");
        }
        if (!TEST.toString().equals("[4, 0, null, 1, 2, 3]")) {
            throw new RuntimeException("Test testAddIndex FAILED");
        }
        System.out.println("Test testAddIndex passed");
    }

    /**
     * The method tests the correctness of the method add
     */
    private void testAdd() {
        TEST.clear();
        int i = 0;
        TEST.add(i++); // [0]
        TEST.add(i++); // [0 ,1]
        TEST.add(i); // [0, 1, 2]
        TEST.add(null); // [0, 1, 2, null]
        if (TEST.size() != 4) {
            throw new RuntimeException("Test testAdd FAILED");
        }
        if (!TEST.toString().equals("[0, 1, 2, null]")) {
            throw new RuntimeException("Test testAdd FAILED");
        }
        System.out.println("Test testAdd passed");
    }
}

