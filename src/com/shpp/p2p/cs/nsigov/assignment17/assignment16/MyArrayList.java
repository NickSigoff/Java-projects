package com.shpp.p2p.cs.nsigov.assignment17.assignment16;

import java.util.Arrays;
import java.util.Iterator;

/**
 * ArrayList is based on dynamic array
 *
 * @param <T> the type of elements held in this queue
 */
public class MyArrayList<T> implements Iterable<T> {
    /**
     * Array capacity at object initialization
     */
    private final int DEFAULT_CAPACITY = 10;

    /**
     * Array size
     */
    private int size;

    /**
     * Internal array size
     */
    private int capacity;

    /**
     * Data storage array
     */
    private Object[] array;

    /**
     * The class constructor calls the method to create the initial array
     */
    public MyArrayList() {
        createArray();
    }

    /**
     * Sets a new value to an element at the entered index
     *
     * @param index index of the required element
     * @param value new value of element
     */
    public void set(int index, T value) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        array[index] = value;
    }

    /**
     * The method creates an array when the class object is initialized
     */
    private void createArray() {
        array = new Object[DEFAULT_CAPACITY];
        capacity = DEFAULT_CAPACITY;
        size = 0;
    }

    /**
     * Adds the argument received to the input to the end of the array
     *
     * @param value The value added to the list
     */
    public void add(T value) {
        if (size == capacity) {
            expandArray();
        }
        array[size] = value;
        size++;
    }

    /**
     * Adds a value to the array at the specified index
     *
     * @param index element insertion index
     * @param value The value added to the list
     */
    public void add(int index, T value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (size == capacity) {
            expandArray();
        }
        Object[] temp = new Object[capacity];
        System.arraycopy(array, 0, temp, 0, index);
        temp[index] = value;
        System.arraycopy(array, index, temp, index + 1, array.length - index - 1);
        array = temp;
        size++;
    }

    /**
     * Returns the element at the entered index
     *
     * @param index element index
     * @return the element at the entered index
     */
    public T get(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        return (T)array[index];
    }

    /**
     * Returns the size of the array
     *
     * @return size of the array
     */
    public int size() {
        return size;
    }

    /**
     * Returns true if the argument received as input is in the array
     *
     * @param value The Search value
     * @return true - element found, false - no element
     */
    public boolean contains(T value) {
        for (int i = 0; i < size; i++) {
            try {
                if (array[i].equals(value)) return true;
            } catch (NullPointerException e) {
                if (value == null) return true;
            }
        }
        return false;
    }

    /**
     * Removes the element at the entered index
     *
     * @param index the index of the element to be removed
     */
    public void remove(int index) {
        if (index < 0 || index > size || size == 0) {
            throw new IndexOutOfBoundsException();
        }
        System.arraycopy(array, index + 1, array, index, --size - index);
    }

    /**
     * Clears the array of all elements
     */
    public void clear() {
        array = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    /**
     * Returns the index of the element received as an argument. Returns -1 if the element is not found
     *
     * @param value The Search value
     * @return the index of the element received as input
     */
    public int indexOf(T value) {
        for (int i = 0; i < size; i++) {
            try {
                if (array[i].equals(value)) {
                    return i;
                }
            } catch (NullPointerException e) {
                if (value == null) return i;
            }
        }
        return -1;
    }

    /**
     * String representation of the list to be output to the console
     *
     * @return String representation of the list
     */
    public String toString() {
        Object[] result = new Object[size];
        System.arraycopy(array, 0, result, 0, size);
        return Arrays.toString(result);
    }

    /**
     * The method defines a new array when the old one is full
     */
    private void expandArray() {
        capacity = (int) (capacity * 1.5 + 1);
        Object[] temp = new Object[capacity];
        System.arraycopy(array, 0, temp, 0, array.length);
        array = temp;
    }

    /**
     * Override method for iterating over a list in a loop
     *
     * @return iterator
     */
    @Override
    public Iterator<T> iterator() {
        Iterator<T> iterator = new Iterator<>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public T next() {
                return (T) array[currentIndex++];
            }
        };
        return iterator;
    }
}
