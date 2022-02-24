package com.shpp.p2p.cs.nsigov.assignment16;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * A stack based on a double linked list
 *
 * @param <T> the type of elements held in this stack
 */
public class MyStack<T> {
    /**
     * Stack size
     */
    private int size;

    /**
     * First node of the stack (head)
     */
    private Node first;

    /**
     * Last node of the stack(tail)
     */
    private Node last;

    /**
     * Adds the argument received to the input to the end of the stack
     *
     * @param value The value added to the stack
     */
    public void push(T value) {
        if (last == null) {
            first = last = new Node(value, null, null);
        } else {
            last.setNext(new Node(value, null, last));
            last = last.getNext();
        }
        size++;
    }

    /**
     * Retrieves and removes the head of this stack, or throws exception if this stack is empty.
     *
     * @return tail meaning
     */
    public T pop() {
        if (last == null) {
            throw new NoSuchElementException();
        }
        Node temp = last;
        last = temp.getPrevious();
        if (last == null) {
            first = null;
        } else {
            last.setNext(null);
        }
        size--;
        try {
            return temp.getValue();
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * Retrieves and doest remove the head of this stack, or throws exception if this stack is empty.
     *
     * @return tail meaning
     */
    public T peek() {
        if (last == null) {
            throw new NoSuchElementException();
        }
        return last.getValue();
    }

    /**
     * The method checks if there are items in the stack
     *
     * @return true elements are, false elements are not
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * Returns the size of the stack
     *
     * @return size of the queue
     */
    public int size() {
        return this.size;
    }

    /**
     * String representation of the stack to be output to the console
     *
     * @return String representation of the stack
     */
    public String toString() {
        Object[] result = this.toArray();
        return Arrays.toString(result);
    }

    /**
     * Returns the stack as an array
     *
     * @return the stack as an array
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
            temp.setPrevious(null);
            temp = next;
        }
        first = last = null;
        size = 0;
    }

    /**
     * The class of the node that is stored in the stack. Has fields in which it stores its own value
     * and links to the previous and next node
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
         * Link to previous node
         */
        private Node previous;

        /**
         * Class constructor. Defining fields value, next, previous
         *
         * @param value    The value stored in the node
         * @param previous Link to previous node
         * @param next     Link to next node
         */
        private Node(T value, Node next, Node previous) {
            this.value = value;
            this.next = next;
            this.previous = previous;
        }

        private T getValue() {
            return value;
        }

        private Node getNext() {
            return next;
        }

        public Node getPrevious() {
            return previous;
        }

        public void setPrevious(Node previous) {
            this.previous = previous;
        }

        private void setNext(Node next) {
            this.next = next;
        }
    }
}

