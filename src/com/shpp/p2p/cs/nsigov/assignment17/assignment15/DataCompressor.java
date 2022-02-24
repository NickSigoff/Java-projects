package com.shpp.p2p.cs.nsigov.assignment17.assignment15;

import com.shpp.p2p.cs.nsigov.assignment17.MyHashMap;
import com.shpp.p2p.cs.nsigov.assignment17.MyPriorityQueue;
import com.shpp.p2p.cs.nsigov.assignment17.assignment16.MyQueue;

import java.io.*;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Comparator;
import java.util.Set;

/**
 * Data compression class.
 * Receives as an input an array of bytes received from ArchiveManager
 */
public class DataCompressor {

    /**
     * Constructed Huffman tree
     */
    private Node huffmanTree;

    /**
     * Huffman tree shape. The shape is written to the archive to restore the tree
     */
    private final MyQueue<Byte> HUFFMAN_SHAPE = new MyQueue<>();

    /**
     * Huffman tree leaves. The leaves are written to the archive to restore the tree
     */
    private final MyQueue<Byte> HUFFMAN_LEAVES = new MyQueue<>();

    /**
     * A table of unique bytes and their encoded values
     */
    private final MyHashMap<Byte, String> TABLE = new MyHashMap<>();

    /**
     * Input file size
     */
    private int inputFileSize;

    /**
     * Input file name
     */
    private final String INPUT_FILE_NAME;

    /**
     * Output file name
     */
    private final String OUTPUT_FILE_NAME;

    /**
     * Class constructor defines class fields INPUT_FILE_NAME, OUTPUT_FILE_NAME
     *
     * @param input_file_name  Input file name
     * @param output_file_name Output file name
     */
    DataCompressor(String input_file_name, String output_file_name) {
        this.INPUT_FILE_NAME = input_file_name;
        this.OUTPUT_FILE_NAME = output_file_name;
    }

    /**
     * The method archives data. Uses a buffer to read and write data piece by piece.
     * Displays service information to the console
     */
    void archive() {
        buildHuffmanTree();
        madeTableFromHuffmanTree(huffmanTree);
        byte[] archivedHuffmanTree = archiveHuffmanTree();
        int outputFileSize = 0;

        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(OUTPUT_FILE_NAME));
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(INPUT_FILE_NAME));
            inputFileSize = bufferedInputStream.available();
            outputFileSize += archivedHuffmanTree.length;
            bufferedOutputStream.write(archivedHuffmanTree);
            byte[] buffer;
            while ((buffer = bufferedInputStream.readNBytes(20000000)).length != 0) {
                byte[] archivedData = archiveData(buffer, bufferedOutputStream);
                outputFileSize += archivedData.length;
                bufferedOutputStream.write(archivedData);
            }
            bufferedOutputStream.close();
            bufferedInputStream.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        System.out.println("Input file size : " + inputFileSize + " Byte");
        System.out.println("Output file size : " + outputFileSize + " Byte");
        System.out.println("Archiving efficiency : " + ((1 - (double) outputFileSize / (double) inputFileSize) * 100) + " %");
    }

    /**
     * The method archives the Huffman tree and returns a data array with information
     * The size of the shape of the trees and the sequence of leaves are written in 2 bytes
     *
     * @return coded huffman tree
     */
    private byte[] archiveHuffmanTree() {
        defineLeaves(huffmanTree);
        defineForm(huffmanTree);
        byte[] archivedHuffmanTree = new byte[4 + HUFFMAN_LEAVES.size() + HUFFMAN_SHAPE.size()];
        byte[] sizeFormTree = ByteBuffer.allocate(2).putShort((short) HUFFMAN_SHAPE.size()).array();
        byte[] sizeLeavesTree = ByteBuffer.allocate(2).putShort((short) HUFFMAN_LEAVES.size()).array();
        //Copying arrays sizeFormTree and sizeLeavesTree to an array archivedHuffmanTree
        System.arraycopy(sizeFormTree, 0, archivedHuffmanTree, 0, sizeFormTree.length);
        System.arraycopy(sizeLeavesTree, 0, archivedHuffmanTree, sizeFormTree.length, sizeLeavesTree.length);
        /*Bytes are sequentially copied from the first of the queue HUFFMAN_SHAPE,
         * when the queue ends, it starts to be copied from queue HUFFMAN_LEAVES
         */
        for (int i = 4; i < archivedHuffmanTree.length; i++) {
            if (HUFFMAN_SHAPE.size() != 0) {
                archivedHuffmanTree[i] = HUFFMAN_SHAPE.poll();
                continue;
            }
            if (HUFFMAN_LEAVES.size() != 0) {
                archivedHuffmanTree[i] = HUFFMAN_LEAVES.poll();
            }
        }
        return archivedHuffmanTree;
    }

    /**
     * The method detects leaves that exist in the Huffman tree
     * Depth search method recursive method
     *
     * @param node Huffman tree (or node) received as input
     */
    private void defineLeaves(Node node) {
        if (node.getLeftBranch() != null && node.getRightBranch() != null) {
            defineLeaves(node.getLeftBranch());
            defineLeaves(node.getRightBranch());
        } else {
            HUFFMAN_LEAVES.add((byte) node.getMeaning());
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
                HUFFMAN_SHAPE.add((byte) 1);
                node.setVisited(true);
            }
            defineForm(node.getLeftBranch());
            defineForm(node.getRightBranch());
        } else {
            HUFFMAN_SHAPE.add((byte) 0);
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
     * The method archives the data received as input. The method builds a stringBuilder from the bytes of the input
     * buffer. There is a problem with significant zeros at the beginning of the sequence. The solution is to add a
     * value to the data equal to the length of the string. When unpacking, this value is read and adds the required
     * number of zeros based on the difference between the received and the initial string
     * The existing BigInteger class is used to convert a string to a byte array
     *
     * @param buffer               Input data array
     * @param bufferedOutputStream A class object to write the length of the line to the file
     * @return archived data
     */
    private byte[] archiveData(byte[] buffer, BufferedOutputStream bufferedOutputStream) throws IOException {
        StringBuilder result = new StringBuilder();
        String stringMeaning;
        for (byte b : buffer) {
            stringMeaning = TABLE.get(b); // get meaning from table
            result.append(stringMeaning);
        }
        byte[] stringLength = ByteBuffer.allocate(4).putInt(result.length()).array();
        bufferedOutputStream.write(stringLength);
        BigInteger resul = new BigInteger(result.toString(), 2);
        return resul.toByteArray();
    }

    /**
     * The method builds a Huffman tree
     * The two objects with the lowest weight are combined into one with the total weight
     */
    private void buildHuffmanTree() {
        MyPriorityQueue<Node> forestTrees = madeQueue();
        while (forestTrees.size() != 1) {
            Node node1 = forestTrees.poll();
            Node node2 = forestTrees.poll();
            forestTrees.add(new Node(node1.getWeight() + node2.getWeight(),
                    node1.getMeaning() + node2.getMeaning(), node1, node2));
        }
        huffmanTree = forestTrees.poll();
    }

    /**
     * The method fills the queue with unique characters according to their frequency of use
     *
     * @return priority Queue with unique bytes
     */
    private MyPriorityQueue<Node> madeQueue() {
        MyHashMap<Byte, Integer> uniqueByte = determineWeightBytes();
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
        MyPriorityQueue<Node> queue = new MyPriorityQueue<>(nodeComparator);

        Set<Byte> keys = uniqueByte.getKeySet(); // fill queue by Nodes
        for (Byte b : keys) {
            queue.add(new Node(uniqueByte.get(b), b));
        }
        return queue;
    }

    /**
     * The method determines how often each character appears in the file.
     *
     * @return HashMap with values of unique bytes with their frequency of occurrence
     */
    private MyHashMap<Byte, Integer> determineWeightBytes() {
        MyHashMap<Byte, Integer> uniqueSymbols = new MyHashMap<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(INPUT_FILE_NAME);
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
}