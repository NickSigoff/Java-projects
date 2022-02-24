package com.shpp.p2p.cs.nsigov.assignment15;

/**
 * The class of the tree node or the huffman tree itself.
 * Contains links to its right and left descendants if this is a node.
 * If this leave stores the value of the encrypted byte and its bitcode
 */
public class Node {

    /**
     * Frequency of occurrence of a node in a file
     */
    private int weight;

    /**
     * Right child node
     */
    private Node rightBranch = null;

    /**
     * left child node
     */
    private Node leftBranch = null;

    /**
     * Byte value
     */
    private int meaning;

    /**
     * Whether the node was visited while traversing the tree
     */
    private boolean isVisited = false;

    /**
     * The bit code of this node
     */
    private String binaryCode = "";

    /**
     * The class constructor will define the fields of the class
     *
     * @param weight      weight value
     * @param meaning     meaning value
     * @param rightBranch rightBranch value
     * @param leftBranch  leftBranch value
     */
    Node(int weight, int meaning, Node rightBranch, Node leftBranch) {
        this.weight = weight;
        this.meaning = meaning;
        this.rightBranch = rightBranch;
        this.leftBranch = leftBranch;
    }

    /**
     * Overloaded constructor only defines fields weight and meaning
     *
     * @param weight  weight value
     * @param meaning meaning value
     */
    Node(int weight, byte meaning) {
        this(weight, meaning, null, null);
    }

    /**
     * Default constructor
     */
    Node() {
    }

    /**
     * Getter for meaning field
     *
     * @return meaning value
     */
    public int getMeaning() {
        return meaning;
    }

    /**
     * Getter for weight field
     *
     * @return field value
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Getter for isVisited field
     *
     * @return isVisited value
     */
    public boolean isVisited() {
        return isVisited;
    }

    /**
     * Getter for rightBranch field
     *
     * @return rightBranch value
     */
    public Node getRightBranch() {
        return rightBranch;
    }

    /**
     * Getter for leftBranch field
     *
     * @return leftBranch value
     */
    public Node getLeftBranch() {
        return leftBranch;
    }

    /**
     * Getter for binaryCode field
     *
     * @return binaryCode value
     */
    public String getBinaryCode() {
        return binaryCode;
    }

    /**
     * Adds (0 or 1) or sequence of zeros and ones to binaryCode field
     *
     * @param binaryCode the value to add to the field
     */
    public void addsBinaryCode(String binaryCode) {
        this.binaryCode += binaryCode;
    }

    /**
     * Setter for rightBranch field
     *
     * @param rightBranch class object Node
     */
    public void setRightBranch(Node rightBranch) {
        this.rightBranch = rightBranch;
    }

    /**
     * Setter for leftBranch field
     *
     * @param leftBranch class object Node
     */
    public void setLeftBranch(Node leftBranch) {
        this.leftBranch = leftBranch;
    }

    /**
     * Setter for meaning field
     *
     * @param meaning integer value of meaning
     */
    public void setMeaning(int meaning) {
        this.meaning = meaning;
    }

    /**
     * Setter for isVisited field
     *
     * @param visited boolean value of isVisited
     */
    public void setVisited(boolean visited) {
        isVisited = visited;
    }
}


