package com.shpp.p2p.cs.nsigov.assignment17;

public class TestMyHashMap {
    /**
     * An instance of the class to test
     */
    private final MyHashMap<String, Integer> TEST = new MyHashMap<>();

    /**
     * Method calls all test functions
     */
    public void runTests() {
        System.out.println("Tests myHashMap:");
        testPut();
        testContainsKey();
        testGet();
        testContainsValue();
        testRemove();
        System.out.println();
    }

    /**
     * Method tests correct work of the remove method
     */
    private void testRemove() {
        if (!TEST.containsKey("0")) {
            throw new RuntimeException("Test testRemove FAILED");
        }
        TEST.remove("0");
        if (TEST.containsKey("0")) {
            throw new RuntimeException("Test testRemove FAILED");
        }
        TEST.clear();
        TEST.put("0", null);
        TEST.put("1", 1);
        TEST.remove("0");
        if (TEST.containsKey("0")) {
            throw new RuntimeException("Test testRemove FAILED");
        }
        TEST.remove("0");  //checking for a non-existent key
        if (TEST.size() != 1) {
            throw new RuntimeException("Test testRemove FAILED");
        }
        System.out.println("Test tesRemove passed");
    }

    /**
     * Method tests correct work of the get method
     */
    private void testGet() {
        // checking first 18 elements
        for (int i = 0; i < 18; i++) {
            if (!TEST.get(String.valueOf(i)).equals(i)) {
                throw new RuntimeException("Test testGet FAILED");
            }
        }
        //checking for a non-existent key
        if (TEST.get("21") != null) {
            throw new RuntimeException("Test testGet FAILED");
        }
        // checking null value
        if (TEST.get("19") != null) {
            throw new RuntimeException("Test testGet FAILED");
        }
        if (TEST.get("20") != null) {
            throw new RuntimeException("Test testGet FAILED");
        }
        System.out.println("Test testGet passed");
    }

    /**
     * Method tests correct work of the containsValue method
     */
    private void testContainsValue() {
        //checking for a non-existent value
        if (TEST.containsValue(30)) {
            throw new RuntimeException("Test testContainsValue FAILED");
        }
        // checking null value
        if (!TEST.containsValue(null)) {
            throw new RuntimeException("Test testContainsValue FAILED");
        }
        // check existing value
        if (!TEST.containsValue(5)) {
            throw new RuntimeException("Test testContainsValue FAILED");
        }
        System.out.println("Test testContainsValue passed");
    }

    /**
     * Method tests correct work of the containsKey method
     */
    private void testContainsKey() {
        // checking first 8 elements
        for (int i = 0; i < 18; i++) {
            if (!TEST.containsKey(String.valueOf(i))) {
                throw new RuntimeException("Test testContainsKey FAILED");
            }
        }
        // checking null value
        if (!TEST.containsKey("19")) {
            throw new RuntimeException("Test testContainsKey FAILED");
        }
        if (!TEST.containsKey("20")) {
            throw new RuntimeException("Test testContainsKey FAILED");
        }
        //checking for a non-existent key
        if (TEST.containsKey("21")) {
            throw new RuntimeException("Test testContainsKey FAILED");
        }
        if (TEST.containsKey("22")) {
            throw new RuntimeException("Test testContainsKey FAILED");
        }
        System.out.println("Test testContainsKey passed");
    }

    /**
     * Method tests correct work of the put method
     */
    private void testPut() {
        // add 20 different values
        for (int i = 0; i < 20; i++) {
            TEST.put(String.valueOf(i), i);
        }
        if (TEST.size() != 20) {
            throw new RuntimeException("Test testPut FAILED");
        }
        // check adding duplicate keys
        TEST.put("18", 100);
        TEST.put("19", 0);

        if (TEST.size() != 20) {
            throw new RuntimeException("Test testPut FAILED");
        }
        //check adding null value
        TEST.put("19", null);
        TEST.put("20", null);
        if (TEST.size() != 21) {
            throw new RuntimeException("Test testPut FAILED");
        }
        // test null key???
        System.out.println("Test testPut passed");
    }
}

