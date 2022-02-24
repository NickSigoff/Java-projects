package com.shpp.p2p.cs.nsigov.assignment15;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * This class contains all the logic of operations with Huffman tree. Class can as creates a tree receiving a file name
 * as restore decoded tree from the data contains tree shape and tree leaves
 */
public class HuffmanTree {

    /**
     * Constructed Huffman tree
     */
    private Node huffmanTree = new Node();

    /**
     * Huffman tree shape. The shape is written to the archive to restore the tree
     */
    private Queue<Byte> huffmanShape = new LinkedList<>();

    /**
     * Huffman tree leaves. The leaves are written to the archive to restore the tree
     */
    private Queue<Byte> huffmanLeaves = new LinkedList<>();

    /**
     * A table of unique bytes and their encoded values
     */
    private final Map<Byte, String> TABLE = new HashMap<>();

    /**
     * A table of unique bytes and their encoded values
     */
    private final Map<String, Byte> RESTORED_TABLE = new HashMap<>();

    /**
     * Input file name
     */
    private String inputFileName;

    /**
     * Constructor builds a new huffman tree
     *
     * @param inputFileName input file name
     */
    HuffmanTree(String inputFileName) {
        this.inputFileName = inputFileName;
        buildHuffmanTree();
    }

    /**
     * Constructor restores a decoded huffman tree
     *
     * @param huffmanShape  shape huffman tree
     * @param huffmanLeaves leaves huffman tree
     */
    public HuffmanTree(Queue<Byte> huffmanShape, Queue<Byte> huffmanLeaves) {
        this.huffmanShape = huffmanShape;
        this.huffmanLeaves = huffmanLeaves;

        restoreHuffmanTree(huffmanTree);
        restoreTable(huffmanTree);
    }


    /**
     * The method detects leaves that exist in the Huffman tree
     * Depth search method, recursive method
     *
     * @param node Huffman tree (or node) received as input
     */
    private void defineLeaves(Node node) {
        if (node.getLeftBranch() != null && node.getRightBranch() != null) {
            defineLeaves(node.getLeftBranch());
            defineLeaves(node.getRightBranch());
        } else {
            huffmanLeaves.add((byte) node.getMeaning());
        }
    }

    /**
     * Method for determining the shape of a Huffman tree
     * Recursive method
     * Each node on the path is one, each leave is zero.
     *
     * @param node Huffman tree (or node) received as input
     */
    private void defineForm(Node node) {
        if (node.getLeftBranch() != null && node.getRightBranch() != null) {
            if (!node.isVisited()) {
                huffmanShape.add((byte) 1);
                node.setVisited(true);
            }
            defineForm(node.getLeftBranch());
            defineForm(node.getRightBranch());
        } else {
            huffmanShape.add((byte) 0);
        }
    }

    /**
     * The method creates a table of unique bytes and their encoded values
     * Recursive method
     * Each turn to the left adds zero to the code sequence, turn to the right one
     *
     * @param node Huffman tree (or node) received as input
     */
    private void madeTableFromHuffmanTree(Node node) {
        if (node.getLeftBranch() != null && node.getRightBranch() != null) {
            String parentCode = node.getBinaryCode();

            Node left = node.getLeftBranch();
            left.addsBinaryCode(parentCode + "0");
            Node right = node.getRightBranch();
            right.addsBinaryCode(parentCode + "1");

            madeTableFromHuffmanTree(left);
            madeTableFromHuffmanTree(right);
        } else {
            TABLE.put((byte) node.getMeaning(), node.getBinaryCode());
        }
    }

    /**
     * The method builds a Huffman tree
     * The two objects with the lowest weight are combined into one with the total weight
     */
    public void buildHuffmanTree() {
        Queue<Node> forestTrees = madeQueue();
        while (forestTrees.size() != 1) {
            Node node1 = forestTrees.remove();
            Node node2 = forestTrees.remove();
            forestTrees.add(new Node(node1.getWeight() + node2.getWeight(),
                    node1.getMeaning() + node2.getMeaning(), node1, node2));
        }
        huffmanTree = forestTrees.remove();
        defineLeaves(huffmanTree);
        defineForm(huffmanTree);
        madeTableFromHuffmanTree(huffmanTree);
    }

    /**
     * The method fills the queue with unique characters according to their frequency of use
     *
     * @return priority Queue with unique bytes
     */
    private Queue<Node> madeQueue() {
        Map<Byte, Integer> uniqueByte = determineWeightBytes();
        if (uniqueByte.size() < 2) {
            System.err.println("There is only one unique character in the file");
            System.exit(1);
        }
        Comparator<Node> nodeComparator = new Comparator<>() { //Comparator to fill the priority queue correctly
            @Override
            public int compare(Node o1, Node o2) {
                return (o1.getWeight() - o2.getWeight());
            }
        };
        Queue<Node> queue = new PriorityQueue<>(nodeComparator);
        for (Map.Entry<Byte, Integer> entry : uniqueByte.entrySet()) { // fill queue by Nodes
            queue.add(new Node(((entry.getValue())), ((entry.getKey()))));
        }
        return queue;
    }

    /**
     * The method determines how often each character appears in the file.
     *
     * @return HashMap with values of unique bytes with their frequency of occurrence
     */
    private Map<Byte, Integer> determineWeightBytes() {
        Map<Byte, Integer> uniqueSymbols = new HashMap<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(inputFileName);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            byte[] buffer;
            while ((buffer = bufferedInputStream.readNBytes(64000)).length != 0) {
                for (byte b : buffer) {
                    if (uniqueSymbols.containsKey(b)) {
                        uniqueSymbols.put(b, uniqueSymbols.get(b) + 1);
                    } else {
                        uniqueSymbols.put(b, 1);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        return uniqueSymbols;
    }

    /**
     * The method restores the shape of a huffman tree
     * Knowing the shape of the tree and the sequence of leaves
     * Recursive method
     *
     * @param node Huffman tree
     */
    private void restoreHuffmanTree(Node node) {
        if (huffmanShape.remove() == 1) {
            Node leftNode = new Node();
            Node rightNode = new Node();
            node.setLeftBranch(leftNode);
            node.setRightBranch(rightNode);
            restoreHuffmanTree(leftNode);
            restoreHuffmanTree(rightNode);
        } else {
            node.setMeaning(huffmanLeaves.remove());
        }

    }

    /**
     * The method restores the table from the restored Huffman tree
     * Recursive method
     * Each turn to the left adds zero to the code sequence, turn to the right one
     *
     * @param node Huffman tree
     */
    private void restoreTable(Node node) {
        if (node.getLeftBranch() != null && node.getRightBranch() != null) {
            String parentCode = node.getBinaryCode();
            Node left = node.getLeftBranch();
            left.addsBinaryCode(parentCode + "0");
            Node right = node.getRightBranch();
            right.addsBinaryCode(parentCode + "1");
            restoreTable(left);
            restoreTable(right);
        } else {
            RESTORED_TABLE.put(node.getBinaryCode(), (byte) node.getMeaning());
        }
    }

    /**
     * Getters for the class fields
     */
    public Map<Byte, String> getTABLE() {
        return TABLE;
    }

    public Map<String, Byte> getRESTORED_TABLE() {
        return RESTORED_TABLE;
    }

    public Queue<Byte> getHuffmanShape() {
        return huffmanShape;
    }

    public Queue<Byte> getHuffmanLeaves() {
        return huffmanLeaves;
    }
}

