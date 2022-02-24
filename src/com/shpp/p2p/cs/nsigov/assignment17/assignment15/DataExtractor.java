package com.shpp.p2p.cs.nsigov.assignment17.assignment15;

import com.shpp.p2p.cs.nsigov.assignment17.MyHashMap;
import com.shpp.p2p.cs.nsigov.assignment17.assignment16.MyArrayList;
import com.shpp.p2p.cs.nsigov.assignment17.assignment16.MyQueue;

import java.io.*;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;


/**
 * The class unpacks the data packed by the DataCompressor class.
 */
public class DataExtractor {

    /**
     * Huffman tree shape. Received from the archive
     */
    private final MyQueue<Byte> HUFFMAN_SHAPE = new MyQueue<>();

    /**
     * Huffman tree leaves. Received from the archive
     */
    private final MyQueue<Byte> HUFFMAN_LEAVES = new MyQueue<>();

    /**
     * Constructed Huffman tree
     */
    private final Node HUFFMAN_TREE = new Node();

    /**
     * A table of unique bytes and their encoded values
     */
    private final MyHashMap<String, Byte> TABLE = new MyHashMap<>();

    /**
     * Input file name
     */
    private final String INPUT_FILE_NAME;

    /**
     * Output file name
     */
    private final String OUTPUT_FILE_NAME;

    /**
     * The class constructor defines the fields of the class
     */
    DataExtractor(String input_file_name, String output_file_name) {
        this.INPUT_FILE_NAME = input_file_name;
        this.OUTPUT_FILE_NAME = output_file_name;
    }

    /**
     * The class works with streams of input / output information.
     * And displays service information to the console
     */
    void unarchive() {
        int inputSize;
        int outputSize = 0;
        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(OUTPUT_FILE_NAME));
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(INPUT_FILE_NAME));
            inputSize = bufferedInputStream.available();
            determineServiceData(bufferedInputStream);
            restoreHuffmanTree(HUFFMAN_TREE);
            restoreTable(HUFFMAN_TREE);
            byte[] buffer;
            while ((buffer = bufferedInputStream.readNBytes(2000000)).length != 0) {
                byte[] unarchivedData = unarchiveData(buffer);
                outputSize += unarchivedData.length;
                bufferedOutputStream.write(unarchivedData);
            }
            bufferedOutputStream.close();
            bufferedInputStream.close();
            System.out.println("Input file size : " + inputSize + " Byte");
            System.out.println("Output file size : " + outputSize + " Byte");
            System.out.println("Archiving efficiency : " + ((1 - (double) inputSize / (double) outputSize) * 100) + " %");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * The method unpacks the data according to the table.
     * The method receives an array of bytes as input. Allocates the packed length of a string. By the difference
     * between the selected line and the received line, it adds the required number of zero to the beginning of the
     * line. Using a class BigInteger, an array of bytes is converted to a string
     *
     * @param buffer array of bytes
     * @return decoded byte array
     * @throws IOException error of file reading
     */
    private byte[] unarchiveData(byte[] buffer) throws IOException {
        byte[] stringSize = Arrays.copyOfRange(buffer, 0, 4);
        byte[] data = Arrays.copyOfRange(buffer, 4, buffer.length);
        ByteBuffer wrapped = ByteBuffer.wrap(stringSize);
        int lenghtArchivedString = (wrapped.getInt());
        BigInteger bigIntegerBuffer = new BigInteger(data);
        StringBuilder stringBuffer = new StringBuilder(bigIntegerBuffer.toString(2)); // "0" +
        int stringBufferlength = stringBuffer.length();
        for (int i = 0; i < lenghtArchivedString - stringBufferlength; i++) {
            stringBuffer.insert(0, "0");
        }
        StringBuilder word = new StringBuilder();
        MyArrayList<Byte> result = new MyArrayList<>();

        for (int i = 0; i < stringBuffer.length(); i++) {
            word.append(stringBuffer.charAt(i));
            if (TABLE.containsKey(word.toString())) {
                result.add(TABLE.get(word.toString()));
                word.setLength(0);
            }
        }
        byte[] arrayResult = new byte[result.size()];
        for (int i = 0; i < result.size(); i++) {
            arrayResult[i] = result.get(i);
        }
        return arrayResult;
    }

    /**
     * The method processes technical information from a packed file. By known bytes, the method fills its variables
     *
     * @param bufferedInputStream class object bufferedInputStream
     */
    private void determineServiceData(BufferedInputStream bufferedInputStream) {
        try {
            byte[] sizeHuffmanShape = bufferedInputStream.readNBytes(2); // 0 - 2 byte size of Huffman Shape
            ByteBuffer wrapped = ByteBuffer.wrap(sizeHuffmanShape);
            short sizeForm = wrapped.getShort();
            byte[] sizeHuffmanLeaves = bufferedInputStream.readNBytes(2); // 2 - 4 byte size of Huffman Leaves
            wrapped = ByteBuffer.wrap(sizeHuffmanLeaves);
            short sizeLeaves = wrapped.getShort();
            byte[] arrayHuffmanTreeForm = bufferedInputStream.readNBytes(sizeForm);
            byte[] arrayHuffmanLeavesForm = bufferedInputStream.readNBytes(sizeLeaves);
            // Makes linked lists from arrays
            for (byte b : arrayHuffmanTreeForm) {
                HUFFMAN_SHAPE.add(b);
            }
            for (byte b : arrayHuffmanLeavesForm) {
                HUFFMAN_LEAVES.add(b);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
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
            TABLE.put(node.getBinaryCode(), (byte) node.getMeaning());
        }
    }

    /**
     * The method restores the shape of a huffman tree
     * Knowing the shape of the tree and the sequence of leaves
     * Recursive method
     *
     * @param node Huffman tree
     */
    private void restoreHuffmanTree(Node node) {
        if (HUFFMAN_SHAPE.poll() == 1) {
            Node leftNode = new Node();
            Node rightNode = new Node();
            node.setLeftBranch(leftNode);
            node.setRightBranch(rightNode);
            restoreHuffmanTree(leftNode);
            restoreHuffmanTree(rightNode);
        } else {
            node.setMeaning(HUFFMAN_LEAVES.poll());
        }
    }
}