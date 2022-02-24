package com.shpp.p2p.cs.nsigov.assignment17.assignment16;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Double linked list class
 *
 * @param <T> the type of elements held in this list
 */
public class MyLinkedList<T> implements Iterable<T> {
    /**
     * List size
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
     * Adds the argument received to the input to the end of the list
     *
     * @param value The value added to the list
     */
    public void add(T value) {
        if (first == null) {
            last = first = new Node(value, last, null);
        } else {
            last.setNext(new Node(value, last, null));
            last = last.getNext();
        }
        size++;
    }

    /**
     * Adds a value to the list at the specified index
     *
     * @param index element insertion index
     * @param value The value added to the list
     */
    public void add(int index, T value) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        } else if (index == 0) {
            addFirst(value);
        } else if (index == size) {
            addLast(value);
            // analysis from the end or the beginning it is more optimal to pass through the elements
        } else if (size / 2 <= index) {
            Node temp;
            temp = last;
            int currentIndex = size;
            while (--currentIndex > index) {
                temp = temp.getPrevious();
            }
            Node insert = new Node(value, temp.getPrevious(), temp);
            insert.getPrevious().setNext(insert);
            insert.getNext().setPrevious(insert);
            size++;
        } else {
            Node temp;
            temp = first;
            int currentIndex = 0;
            while (++currentIndex < index) {
                temp = temp.getNext();
            }
            Node insert = new Node(value, temp, temp.getNext());
            insert.getPrevious().setNext(insert);
            insert.getNext().setPrevious(insert);
            size++;
        }
    }

    /**
     * Adds a value to the beginning of the list
     *
     * @param value The value added to the list
     */
    public void addFirst(T value) {
        if (first == null) {
            last = first = new Node(value, null, null);
        } else {
            first.setPrevious(new Node(value, null, first));
            first = first.getPrevious();
        }
        size++;
    }

    /**
     * Adds a value to the end of the list
     *
     * @param value The value added to the list
     */
    public void addLast(T value) {
        if (last == null) {
            first = last = new Node(value, null, null);
        } else {
            last.setNext(new Node(value, last, null));
            last = last.getNext();
        }
        size++;
    }

    /**
     * String representation of the list to be output to the console
     *
     * @return String representation of the list
     */
    public String toString() {
        Object[] result = this.toArray();
        return Arrays.toString(result);
    }

    /**
     * Returns the size of the list
     *
     * @return size of the list
     */
    public int size() {
        return size;
    }

    /**
     * Returns the element at the entered index
     *
     * @param index element index
     * @return the element at the entered index
     */
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        } else if (index == 0) {
            return getFirst();
        } else if (index == size - 1) {
            return getLast();
            // analysis from the end or the beginning it is more optimal to pass through the elements
        } else if (size / 2 <= index) {
            Node temp;
            temp = last;
            int currentIndex = size;
            while (--currentIndex > index) {
                temp = temp.getPrevious();
            }
            return temp.getValue();
        } else {
            Node temp;
            temp = first;
            int currentIndex = 0;
            while (++currentIndex < index) {
                temp = temp.getNext();
            }
            return temp.getNext().getValue();
        }
    }

    /**
     * Returns the first element of the list
     *
     * @return the first element of the list
     */
    public T getFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return first.getValue();
    }

    /**
     * Returns the last element of the list
     *
     * @return the last element of the list
     */
    public T getLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return last.getValue();
    }

    /**
     * Returns the index of the element received as an argument. Returns -1 if the element is not found
     *
     * @param value The Search value
     * @return the index of the element received as input
     */
    public int indexOf(T value) {
        Node temp = first;
        int index = -1;
        if (value == null) {
            while (++index < size) {
                try {
                    temp.getValue().equals(null);
                } catch (NullPointerException e) {
                    return index;
                }
                temp = temp.getNext();
            }
        } else {
            while (++index < size && !temp.getValue().equals(value)) {
                temp = temp.getNext();
            }
        }
        return index == size ? -1 : index;
    }

    /**
     * Returns true if the argument received as input is in the list
     *
     * @param value The Search value
     * @return true - element found, false - no element
     */
    public boolean contains(T value) {
        Node temp = first;
        int index = -1;
        if (value == null) {
            while (++index < size) {
                try {
                    temp.getValue().equals(null);
                } catch (NullPointerException e) {
                    return true;
                }
                temp = temp.getNext();
            }
        } else {
            while (++index < size && !temp.getValue().equals(value)) {
                temp = temp.getNext();
            }
        }
        return index < size;
    }

    /**
     * Removes the element at the entered index
     *
     * @param index the index of the element to be removed
     */
    public void remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        } else if (index == 0) {
            removeFirst();
        } else if (index == size - 1) {
            removeLast();
            // analysis from the end or the beginning it is more optimal to pass through the elements
        } else if (size / 2 <= index) {
            Node temp;
            temp = last;
            int currentIndex = size;
            while (--currentIndex > index) {
                temp = temp.getPrevious();
            }
            temp.getPrevious().setNext(temp.getNext());
            size--;
        } else {
            Node temp;
            temp = first;
            int currentIndex = 0;
            while (++currentIndex < index) {
                temp = temp.getNext();
            }
            temp = temp.getNext();
            temp.getPrevious().setNext(temp.getNext());
            size--;
        }

    }

    /**
     * Removes the first element of the list
     */
    public void removeFirst() {
        if (first == null) {
            throw new NoSuchElementException();
        }
        if (first.getNext() == null) {
            first = last = null;
        } else {
            first = first.getNext();
            first.setPrevious(null);
        }
        size--;
    }

    /**
     * Removes the last element of the list
     */
    public void removeLast() {
        if (last == null) {
            throw new NoSuchElementException();
        }
        if (last.getPrevious() == null) {
            first = last = null;
        } else {
            last = last.getPrevious();
            last.setNext(null);
        }
        size--;
    }

    /**
     * Sets a new value to an element at the entered index
     *
     * @param index index of the required element
     * @param value new value of element
     */
    public void set(int index, T value) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        } else if (index == 0) {
            first.setValue(value);
        } else if (index == size - 1) {
            last.setValue(value);
            // analysis from the end or the beginning it is more optimal to pass through the elements
        } else if (size / 2 <= index) {
            Node temp;
            temp = last;
            int currentIndex = size;
            while (--currentIndex > index) {
                temp = temp.getPrevious();
            }
            temp.setValue(value);
        } else {
            Node temp;
            temp = first;
            int currentIndex = 0;
            while (++currentIndex < index) {
                temp = temp.getNext();
            }
            temp = temp.getNext();
            temp.setValue(value);
        }

    }

    /**
     * Clears the list of all elements
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
                return get(currentIndex++);
            }
        };
        return iterator;
    }

    /**
     * Returns the list as an array
     *
     * @return the list as an array
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
     * The class of the node that is stored in the list. Has fields in which it stores its own value
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
        public Node(T value, Node previous, Node next) {
            this.value = value;
            this.previous = previous;
            this.next = next;
        }

        private T getValue() {
            return value;
        }

        private Node getNext() {
            return next;
        }

        private Node getPrevious() {
            return previous;
        }

        private void setValue(T value) {
            this.value = value;
        }

        private void setNext(Node next) {
            this.next = next;
        }

        private void setPrevious(Node previous) {
            this.previous = previous;
        }
    }
}
