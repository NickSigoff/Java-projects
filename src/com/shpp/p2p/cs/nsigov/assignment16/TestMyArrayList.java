package com.shpp.p2p.cs.nsigov.assignment16;

import java.util.NoSuchElementException;

public class TestMyArrayList {
    private final MyArrayList<Integer> TEST = new MyArrayList<>();

    public void runTests() {
        System.out.println("Tests myArrayList:");
        testAdd();
        testAddIndex();
        testContains();
        testSet();
        testRemove();
        testGet();
        testIndexOf();
        System.out.println();
    }

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

    private void testIndexOf() {
        // test = [1, 0, 1, 2, 3, 4, 5, 6, 7, 8]
        TEST.clear();
        TEST.add(555);
        if (TEST.indexOf(555) != 0) { // only one element
            throw new RuntimeException("Test testIndexOf FAILED");
        }
        TEST.add(TEST.size() - 1, 55); // [55, 555]
        TEST.add(TEST.size(), 5); // [55, 555, 5]
        TEST.add(55); // [55, 555, 5, 55]
        TEST.add(5); // [55, 555, 5, 55, 5]
        if (TEST.indexOf(5555) != -1) {
            throw new RuntimeException("Test testIndexOf FAILED");
        }
        if (TEST.indexOf(5) != 2) {
            throw new RuntimeException("Test testIndexOf FAILED");
        }
        if (TEST.indexOf(55) != 0) {
            throw new RuntimeException("Test testIndexOf FAILED");
        }
        if (TEST.indexOf(555) != 1) {
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
        TEST.add(5);
        if (TEST.indexOf(null) != 0) {
            throw new RuntimeException("Test testIndexOf FAILED");
        }
    }

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

    private void testContains() {
        // test = [4, 0, null, 1, 2, 3]
        if (!TEST.contains(3)) {
            throw new RuntimeException("Test testContains FAILED");
        }
        if (TEST.contains(5)) {
            throw new RuntimeException("Test testContains FAILED");
        }
        if (!TEST.contains(null)) {
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
        TEST.add(null);
        if (!TEST.contains(null)) { // if 2 null Nodes are into the list
            throw new RuntimeException("Test testContains FAILED");
        }
        TEST.clear();
        if (TEST.contains(null)) { // if null Nodes are into the list after clear
            throw new RuntimeException("Test testContains FAILED");
        }
        System.out.println("Test testContains passed");
    }


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
        if (!TEST.get(7).equals(5)) {
            throw new RuntimeException("Test testGet FAILED");
        }
        try {
            TEST.get(-1); // negative index
        } catch (IndexOutOfBoundsException e) {
            TEST.remove(TEST.size() - 1);  // [0, 1, 0, 1, 2, 3, 4, 5, 6, 7, 8]
        }
        try {
            TEST.get(20); // oversize index
        } catch (IndexOutOfBoundsException e) {
            TEST.remove(0); // [1, 0, 1, 2, 3, 4, 5, 6, 7, 8]
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
        TEST.remove(TEST.size() - 1);
        System.out.println("Test testGet passed");
    }

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
