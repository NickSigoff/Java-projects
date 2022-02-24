package com.shpp.p2p.cs.nsigov.assignment17;

import java.util.HashSet;
import java.util.Set;

/**
 * Class MyHashMap is an attempt to write a class which would be similar original class HashMap,
 * has similar methods and works like the original  HashMap. In the basis of the class lays array which stores all
 * the elements added to it. In the case collision and attempt to write value in an engaged cell,
 * the values will be stored as a linked list
 *
 * @param <K> the type of elements stored in this HasHMap as a key
 * @param <V> the type of elements stored in this HasHMap as a value
 */

public class MyHashMap<K, V> {
    /**
     * The main array where the values will be stored
     */
    private Node<K, V>[] array;

    /**
     * The coefficient shows the ratio of the number of occupied cells to empty one's.
     * When it reaches 0.75 and above, a new larger array will create.
     */
    private static final double LOAD_FACTOR = 0.75;

    /**
     * The initial size of the array that the array has when a new HashMap is created.
     */
    private static final int INITIAL_SIZE_ARRAY = 16;

    /**
     * Number of elements that have been stored in the HashMap
     */
    private int size;

    /**
     * Constructor of the class
     */
    public MyHashMap() {
        clear();
    }

    /**
     * The method returns a set of keys. The method is used to iterate over the HashMap
     *
     * @return set all the keys stored in the HashMap
     */
    public Set<K> getKeySet() {
        Set<K> result = new HashSet<>();
        for (Node<K, V> node : array) {
            while (node != null) {
                result.add(node.getKey());
                node = node.getNext();
            }
        }
        return result;
    }

    /**
     * The method creates new inner array to nulls all the elements
     */
    public void clear() {
        array = new Node[INITIAL_SIZE_ARRAY];
        size = 0;
    }

    /**
     * Method checks if there is the key inputted as an argument of the method in the HashMap
     *
     * @param key Key to be checked
     * @return true - HashMap has the key. false - doesn't have
     */
    public boolean containsKey(K key) {
        return indexOf(key) != -1;
    }

    /**
     * This method returns the index of the inputted key or returns -1 if the inputted key doesn't exist
     *
     * @param key Key to be checked
     * @return -1 HashMap doesn't have such key, or the index of the key
     */
    private int indexOf(K key) {
        Node<K, V> temp = array[calculateIndex(key)];

        for (; temp != null; temp = temp.getNext()) {
            if (key.hashCode() == (temp.getKey()).hashCode()) {
                return calculateIndex(key);
            }
        }
        return -1;
    }

    /**
     * Method checks if there is the value inputted as an argument of the method in the HashMap
     *
     * @param value Key to be checked
     * @return true - HashMap has the value. false - doesn't have
     */
    public boolean containsValue(V value) {
        boolean result = false;
        for (Node<K, V> kvNode : array) {
            for (; kvNode != null; kvNode = kvNode.getNext()) {
                try {
                    if (kvNode.getValue().equals(value)) {
                        result = true;
                        break;
                    }
                } catch (NullPointerException e) {
                    if (value == null) {
                        result = true;
                        break;
                    }
                }
            }
        }
        return result;
    }

    /**
     * The method returns a value of the inputted key. If Hashmap doesn't have the key returns null
     *
     * @param key Key to be checked
     * @return value of the key
     */
    public V get(K key) {
        if (!this.containsKey(key)) {
            return null;
        }

        Node<K, V> temp = array[calculateIndex(key)];
        while (!key.equals(temp.getKey())) {
            temp = temp.getNext();
        }
        return temp.getValue();
    }

    /**
     * Method puts the inputted value to the HashMap. If such key is already stored rewrites its value
     *
     * @param key   added key
     * @param value added value
     */
    public void put(K key, V value) {
        expandArray();
        if (this.get(key) == null) { // if such key doesn't exist
            addValue(key, value);
        } else {
            setValue(key, value);
        }
    }

    /**
     * Private method adds the inputted value to the HashMap.This method is used if HashMap doesn't contain
     * the inputted value
     *
     * @param key   added key
     * @param value added value
     */
    private void addValue(K key, V value) {
        int index = calculateIndex(key);
        if (array[index] == null) {
            array[index] = new Node<>(key, value, null);
        } else {
            Node<K, V> temp = array[index];
            while (temp.getNext() != null) {
                temp = temp.getNext();
            }
            temp.setNext(new Node<>(key, value, null));
        }
        size++;
    }

    /**
     * Private method is used in the put method. The method is called when the inputted key already
     * exist and need to overwrite its value
     *
     * @param key   added key
     * @param value added value
     */
    private void setValue(K key, V value) {
        Node<K, V> temp = array[calculateIndex(key)];

        while (!key.equals(temp.getKey())) {
            temp = temp.getNext();
        }
        temp.setValue(value);
    }

    /**
     * The method creates new inner array when value of LOAD_FACTOR has been reached
     */
    private void expandArray() {
        if (size > array.length * LOAD_FACTOR) {
            Node<K, V>[] temp = array;
            array = new Node[temp.length * 2];
            size = 0;
            for (int i = 0; i < temp.length; i++) {
                if (temp[i] != null) {
                    for (; temp[i] != null; temp[i] = temp[i].getNext()) {
                        this.put(temp[i].getKey(), temp[i].getValue());
                    }
                }
            }
        }
    }

    /**
     * The method removes the element with the entered key
     * Returns true if the deletion was successfully, false if the entered key was not found
     *
     * @param key key to remove
     * @return Returns true if the deletion was successfully, false if the entered key was not found
     */
    public boolean remove(K key) {
        int index = indexOf(key);
        if (index == -1) {
            return false;
        }
        boolean result = false;
        Node<K, V> temp = array[index];
        if (!key.equals(temp.getKey())) {
            while (!key.equals(temp.getNext().getKey())) {
                temp = temp.getNext();
            }
            if (temp.getNext().getNext() != null) {
                temp.setNext(temp.getNext().getNext());
            } else {
                temp.setNext(null);
            }
            result = true;
        }
        if (!result) {
            array[index] = array[index].getNext();
        }
        size--;
        return true;
    }

    /**
     * Returns HashMap size
     *
     * @return HashMap size
     */
    public int size() {
        return size;
    }

    /**
     * This method calculates an index by the inputted key
     *
     * @param key Node key
     * @return the index of the cell with the node containing the inputted key
     */
    private int calculateIndex(K key) {
        return (key.hashCode() & (array.length - 1));
    }

    /**
     * The class of the node that is stored in the HashMap.
     */
    private static class Node<K, V> {

        /**
         * The key is stored in this node
         */
        private K key;

        /**
         * The value stored in the node
         */
        private V value;

        /**
         * Link to the next node
         */
        private Node<K, V> next;

        /**
         * Class constructor. Defining fields value, next, key
         *
         * @param value The value stored in the node
         * @param next  Link to next node
         * @param key   The key is stored in this node
         */
        public Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        /**
         * Getters and setters for the class fields
         */
        public void setValue(V value) {
            this.value = value;
        }

        public void setNext(Node<K, V> next) {
            this.next = next;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public Node<K, V> getNext() {
            return next;
        }
    }
}