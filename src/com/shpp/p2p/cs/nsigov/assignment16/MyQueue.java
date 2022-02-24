package com.shpp.p2p.cs.nsigov.assignment16;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * A queue based on a single linked list
 *
 * @param <T> the type of elements held in this queue
 */
public class MyQueue<T> {
    /**
     * Queue size
     */
    private int size;

    /**
     * First node of the list (head)
     */
    private Node first;

    /**
     * Last node of the list(tail)
     */
    private Node last;

    /**
     * Adds the argument received to the input to the end of the queue
     *
     * @param value The value added to the list
     */
    public void add(T value) {
        if (first == null) {
            first = last = new Node(value, null);
        } else {
            last.setNext(new Node(value, null));
            last = last.getNext();
        }
        size++;
    }

    /**
     * Retrieves and removes the head of this queue, or throws exception if this queue is empty.
     *
     * @return head meaning
     */
    public T poll() {
        if (first == null) {
            throw new NoSuchElementException();
        }
        Node temp = first;
        first = first.getNext();
        size--;
        try {
            return temp.getValue();
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * Retrieves and doesn't remove the head of this queue, or throws exception if this queue is empty.
     *
     * @return head meaning
     */
    public T peek() {
        if (first == null) {
            throw new NoSuchElementException();
        }
        return first.getValue();
    }

    /**
     * The method checks if there are items in the queue
     *
     * @return true elements are, false elements are not
     */
    public boolean isEmpty() {
        return first == null;
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
        Object[] result = this.toArray();
        return Arrays.toString(result);
    }

    /**
     * Returns the queue as an array
     *
     * @return the queue as an array
     */
    public Object[] toArray() {
        int i = 0;
        Object[] result = new Object[size];
        Node temp = first;
        while (temp != null) {
            result[i++] = temp.getValue();
            temp = temp.getNext();
        }
        return result;
    }

    /**
     * Clears the queue of all elements
     */
    public void clear() {
        Node temp = first;
        while (temp != null) {
            Node next = temp.getNext();
            temp.setNext(null);
            temp = next;
        }
        first = last = null;
        size = 0;
    }

    /**
     * The class of the node that is stored in the queue. Has fields in which it stores its own value
     * and a link to the next node
     */
    private class Node {

        /**
         * The value stored in the node
         */
        private T value;

        /**
         * Link to next node
         */
        private Node next;

        /**
         * Class constructor. Defining fields value, next
         *
         * @param value The value stored in the node
         * @param next  Link to next node
         */
        private Node(T value, Node next) {
            this.value = value;
            this.next = next;
        }

        private T getValue() {
            return value;
        }

        private Node getNext() {
            return next;
        }

        private void setNext(Node next) {
            this.next = next;
        }
    }
}





