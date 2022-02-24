package com.shpp.p2p.cs.nsigov.assignment17;

import com.shpp.p2p.cs.nsigov.assignment17.assignment16.MyArrayList;

import java.util.Comparator;
import java.util.Iterator;

/**
 * Class MyPriorityQueue is an attempt to create a class similar to  the existing Java class PriorityQueue
 * The class has similar methods the same as in PriorityQueue, providing basic work with this data structure.
 * Like in the priority queue, the comparator can be overridden if necessary.
 * The prioritization algorithm is built on a binary heap
 *
 * @param <T> the type of elements stored in this queue
 */
public class MyPriorityQueue<T> implements Iterable<T> {
    /**
     * An inner array which stores all the elements and their order
     */
    private MyArrayList<T> array;

    /**
     * Elements number
     */
    private int size;

    /**
     * The order removing and adding the elements from a queue
     */
    private final Comparator<T> COMPARATOR;

    /**
     * The class constructor initializes the internal array and comparator by default
     */
    public MyPriorityQueue() {
        clear();
        COMPARATOR = (o1, o2) -> o1 instanceof Comparable ? ((Comparable<T>) o1).compareTo(o2) : 0;
    }

    /**
     * The class constructor gets a comparator as an argument and initializes it
     *
     * @param comparator another comparator
     */
    public MyPriorityQueue(Comparator<T> comparator) {
        clear();
        this.COMPARATOR = comparator;
    }

    /**
     * Adds the argument received to the input to the queue
     *
     * @param value value to add
     */
    public void add(T value) {
        // PrioryQueue doesn't work with null value
        if (value == null) {
            throw new NullPointerException();
        }
        array.add(value);
        if (size != 0) {
            restoreOrderUp();
        }
        size++;
    }

    /**
     * This method restores the order of the elements in the binary heap. The method determines the correct place
     * for the last element
     */
    private void restoreOrderUp() {
        int parentIndex = calculateParentIndex(size);
        if (COMPARATOR.compare(array.get(size), array.get(parentIndex)) <= 0) {
            int index = size;
            while ((COMPARATOR.compare(array.get(index), array.get(parentIndex)) < 0)) {
                swap(index, parentIndex);
                parentIndex = calculateParentIndex(index);
                if (index == 0) {
                    break;
                }
            }
        }
    }

    /**
     * Method swaps two elements between each other
     *
     * @param indexFirst  the first index
     * @param indexSecond the second index
     */
    private void swap(int indexFirst, int indexSecond) {
        T temp = array.get(indexFirst);
        array.set(indexFirst, array.get(indexSecond));
        array.set(indexSecond, temp);
    }

    /**
     * Method calculates index of parents for the element with inputted as an argument index
     *
     * @param index the index of the element for which the parent is being searched
     */
    private int calculateParentIndex(int index) {
        return (index + 1) / 2 - 1;
    }

    /**
     * Retrieves and removes the head of this queue, or returns null if this queue is empty.
     *
     * @return head meaning
     */
    public T poll() {
        if (this.peek() == null) {
            return null;
        }
        T first = array.get(0);
        array.set(0, array.get(array.size() - 1));
        array.remove(array.size() - 1);
        size--;

        if (size > 1) {
            restoreOrderDown();
        }
        return first;
    }

    /**
     * This method restores the order of the elements in the binary heap. The method determines the correct place
     * for the first element
     */
    private void restoreOrderDown() {
        int index = 0;
        int childIndex = calculateChildIndex(index);
        while (COMPARATOR.compare(array.get(index), array.get(childIndex)) > 0 ||
                COMPARATOR.compare(array.get(index), array.get(childIndex - 1)) > 0) {
            if (COMPARATOR.compare(array.get(childIndex), array.get(childIndex - 1)) >= 0) {
                swap(childIndex - 1, index);
                index = childIndex - 1;
            } else {
                swap(childIndex, index);
                index = childIndex;
            }
            childIndex = calculateChildIndex(index);
            if (childIndex - 1 >= size) {
                break;
            }
        }
    }

    /**
     * Method calculates index of child for the element with inputted as an argument index
     *
     * @param index the index of the element for which the child is being searched
     * @return child index
     */
    private int calculateChildIndex(int index) {
        return (index + 1) * 2;
    }

    /**
     * Retrieves and doesn't remove the head of this queue, or returns null if this queue is empty.
     *
     * @return head meaning
     */
    public T peek() {
        return size == 0 ? null : array.get(0);
    }

    /**
     * The method checks if there are items in the queue
     *
     * @return true elements are, false elements are not
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the size of the queue
     *
     * @return size of the queue
     */
    public int size() {
        return this.size;
    }

    /**
     * String representation of the queue to be output to the console
     *
     * @return String representation of the queue
     */
    public String toString() {
        return array.toString();
    }

    /**
     * Clears the queue of all elements
     */
    public void clear() {
        array = new MyArrayList<>();
        size = 0;
    }


    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public T next() {
                return array.get(currentIndex++);
            }
        };
    }
}

