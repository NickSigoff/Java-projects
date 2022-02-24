package com.shpp.p2p.cs.nsigov.assignment15;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;

/**
 * The class unpacks the data packed by the DataCompressor class.
 */
public class DataExtractor {

    /**
     * Value of key gor searching in the table
     */
    private StringBuilder keyString = new StringBuilder();

    /**
     * Unarchive file size
     */
    private long unarchiveSize;

    /**
     * Buffer size
     */
    private static final int BUFFER_SIZE = 64000;

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

        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(OUTPUT_FILE_NAME));
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(INPUT_FILE_NAME));
            HuffmanTree huffmanTree = unarchiveHuffmanTree(bufferedInputStream);
            long inputSize = bufferedInputStream.available();
            long output = unarchiveSize;

            Map<String, Byte> table = huffmanTree.getRESTORED_TABLE();
            byte[] buffer;
            while ((buffer = bufferedInputStream.readNBytes(BUFFER_SIZE)).length != 0) {

                byte[] unarchivedData = unarchiveData(table, buffer);
                bufferedOutputStream.write(unarchivedData);
            }
            bufferedOutputStream.close();
            bufferedInputStream.close();
            showInformation(inputSize, output);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * This method displays information about coding to the console
     *
     * @param inputSize input file size in bytes
     * @param output    output file size in bytes
     */
    private void showInformation(long inputSize, long output) {
        System.out.println("Input file size : " + inputSize + " Byte");
        System.out.println("Output file size : " + output + " Byte");
        System.out.println("Archiving efficiency : " + ((1 - (double) inputSize / (double) output) * 100) + " %");
    }

    /**
     * The method unpacks the data according to the table.
     *
     * @param table  coded sequence table
     * @param buffer array of bytes
     * @return decoded byte array
     * @throws IOException error of file reading
     */
    private byte[] unarchiveData(Map<String, Byte> table, byte[] buffer) throws IOException {
        StringBuilder allBuffer = new StringBuilder(buffer.length);
        byte[] byteArray = new byte[buffer.length * 10];
        int i = 0;
        while (i < buffer.length) {
            allBuffer.append(byteToBinaryString(buffer[i++]));
        }
        i = 0;
        for (int j = 0; j < allBuffer.length(); j++) {
            keyString.append(allBuffer.charAt(j));
            if (table.containsKey(keyString.toString()) && unarchiveSize > 0) {
                byteArray[i++] = table.get(keyString.toString());
                unarchiveSize--;
                keyString.setLength(0);
            }
        }
        return Arrays.copyOf(byteArray, i);
    }

    /**
     * This method transforms bytes to their binary string value
     *
     * @param b byte to transform
     * @return binary string value
     */
    private String byteToBinaryString(byte b) {
        StringBuilder result = new StringBuilder();
        byte temp;
        int lastBit;
        for (int i = 0; i < 8; i++) {
            temp = (byte) (b >> i);
            lastBit = temp & 0b00000001;
            result.append(lastBit);
        }
        return result.reverse().toString();
    }


    /**
     * The method processes technical information from a packed file. By known bytes, the method fills its variables
     *
     * @param bufferedInputStream class object bufferedInputStream
     */
    public HuffmanTree unarchiveHuffmanTree(BufferedInputStream bufferedInputStream) {
        Queue<Byte> huffmanShape = new LinkedList<>();
        Queue<Byte> huffmanLeaves = new LinkedList<>();

        try {
            byte[] unarchiveFileSize = bufferedInputStream.readNBytes(8); // 0 - 8 byte size the outputted file
            ByteBuffer wrapped = ByteBuffer.wrap(unarchiveFileSize);
            unarchiveSize = wrapped.getLong();
            byte[] sizeHuffmanShape = bufferedInputStream.readNBytes(2); // 9 - 10 byte size of Huffman Shape
            wrapped = ByteBuffer.wrap(sizeHuffmanShape);
            short sizeForm = wrapped.getShort();
            byte[] sizeHuffmanLeaves = bufferedInputStream.readNBytes(2); // 11 - 12 byte size of Huffman Leaves
            wrapped = ByteBuffer.wrap(sizeHuffmanLeaves);
            short sizeLeaves = wrapped.getShort();

            byte[] arrayHuffmanTreeForm = bufferedInputStream.readNBytes(sizeForm);
            byte[] arrayHuffmanLeavesForm = bufferedInputStream.readNBytes(sizeLeaves);
            // Makes linked lists from arrays
            for (byte b : arrayHuffmanTreeForm) {
                huffmanShape.add(b);
            }
            for (byte b : arrayHuffmanLeavesForm) {
                huffmanLeaves.add(b);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        return new HuffmanTree(huffmanShape, huffmanLeaves);
    }
}
