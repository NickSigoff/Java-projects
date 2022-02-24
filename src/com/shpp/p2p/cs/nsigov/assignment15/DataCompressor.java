package com.shpp.p2p.cs.nsigov.assignment15;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;

/**
 * Data compression class.
 * Receives as an input an array of bytes received from ArchiveManager
 */
public class DataCompressor {

    /**
     * Field which stores binary symbols to add them to the output array
     */
    private static final StringBuilder codedByteBuffer = new StringBuilder();

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
     * Class constructor defines class fields INPUT_FILE_NAME, OUTPUT_FILE_NAME
     *
     * @param inputFileName  Input file name
     * @param outputFileName Output file name
     */
    DataCompressor(String inputFileName, String outputFileName) {
        this.INPUT_FILE_NAME = inputFileName;
        this.OUTPUT_FILE_NAME = outputFileName;
    }

    /**
     * The method archives data. Uses a buffer to read and write data piece by piece.
     * Displays service information to the console
     */
    void archive() {
        HuffmanTree huffmanTree = new HuffmanTree(INPUT_FILE_NAME);
        Map<Byte, String> table = huffmanTree.getTABLE();

        long inputFileSize = 0;
        long outputFileSize = 0;

        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(OUTPUT_FILE_NAME));
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(INPUT_FILE_NAME));
            inputFileSize = bufferedInputStream.available();
            // archives and writes the Huffman tree
            byte[] archivedHuffmanTree = archiveHuffmanTree(inputFileSize, huffmanTree);
            outputFileSize += archivedHuffmanTree.length;
            bufferedOutputStream.write(archivedHuffmanTree);
            // archives and writes rest file
            byte[] buffer;
            boolean lastBuffer = false;
            while ((buffer = bufferedInputStream.readNBytes(BUFFER_SIZE)).length != 0) {
                if (buffer.length < BUFFER_SIZE) {
                    lastBuffer = true;
                }
                byte[] archivedData = archiveData(buffer, table, lastBuffer);
                outputFileSize += archivedData.length;
                bufferedOutputStream.write(archivedData);
            }
            bufferedOutputStream.close();
            bufferedInputStream.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        showInformation(inputFileSize, outputFileSize);
    }

    /**
     * This method displays information about coding to the console
     *
     * @param inputFileSize  input file size in bytes
     * @param outputFileSize output file size in bytes
     */
    private void showInformation(long inputFileSize, long outputFileSize) {
        System.out.println("Input file size : " + inputFileSize + " Byte");
        System.out.println("Output file size : " + outputFileSize + " Byte");
        System.out.println("Archiving efficiency : " + ((1 - (double) outputFileSize / (double) inputFileSize) * 100) + " %");
    }

    /**
     * The method archives the Huffman tree and returns a data array with information
     * The size of the shape of the trees and the sequence of leaves are written in 2 bytes
     *
     * @param inputSize   the input file size
     * @param huffmanTree a huffman tree
     * @return coded huffman tree
     */
    public byte[] archiveHuffmanTree(long inputSize, HuffmanTree huffmanTree) {
        Queue<Byte> huffmanShape = huffmanTree.getHuffmanShape();
        Queue<Byte> huffmanLeaves = huffmanTree.getHuffmanLeaves();

        // 12 - size of Leaves and Shape + 8 (long) input file size
        byte[] archivedHuffmanTree = new byte[12 + huffmanLeaves.size() + huffmanShape.size()];

        byte[] inputFileSize = ByteBuffer.allocate(8).putLong(inputSize).array();
        byte[] sizeFormTree = ByteBuffer.allocate(2).putShort((short) huffmanShape.size()).array();
        byte[] sizeLeavesTree = ByteBuffer.allocate(2).putShort((short) huffmanLeaves.size()).array();
        //Copying arrays inputFileSize, sizeFormTree and sizeLeavesTree to the array archivedHuffmanTree
        System.arraycopy(inputFileSize, 0, archivedHuffmanTree, 0, inputFileSize.length);
        System.arraycopy(sizeFormTree, 0, archivedHuffmanTree, inputFileSize.length, sizeFormTree.length);
        System.arraycopy(sizeLeavesTree, 0, archivedHuffmanTree, inputFileSize.length + sizeFormTree.length, sizeLeavesTree.length);
        /*Bytes are sequentially copied from the first of the queue HUFFMAN_SHAPE,
         * when the queue ends, it starts to be copied from queue HUFFMAN_LEAVES
         */
        for (int i = 12; i < archivedHuffmanTree.length; i++) {
            if (huffmanShape.size() != 0) {
                archivedHuffmanTree[i] = huffmanShape.remove();
                continue;
            }
            if (huffmanLeaves.size() != 0) {
                archivedHuffmanTree[i] = huffmanLeaves.remove();
            }
        }
        return archivedHuffmanTree;
    }

    /**
     * This method gets buffers of data to archive them.
     *
     * @param buffer     data buffer with not encoded data
     * @param table      coded sequence table
     * @param lastBuffer a marker which shows the buffer is the last one?
     * @return decoded data to write
     */
    public byte[] archiveData(byte[] buffer, Map<Byte, String> table, boolean lastBuffer) {
        byte[] output = new byte[determineOutputArraySize(table, buffer, lastBuffer)];
        String stringByte;
        int intByte;
        int i = 0;
        for (byte b : buffer) {
            if (codedByteBuffer.append(table.get(b)).length() < Byte.SIZE) {
                continue;
            }
            while (codedByteBuffer.length() >= Byte.SIZE) {
                stringByte = codedByteBuffer.substring(0, Byte.SIZE);
                codedByteBuffer.delete(0, Byte.SIZE);
                intByte = Integer.valueOf(stringByte, 2);
                output[i++] = (byte) intByte;
            }
        }
        if (lastBuffer && codedByteBuffer.length() != 0) {
            stringByte = codedByteBuffer.toString();
            intByte = Integer.valueOf(stringByte, 2);
            intByte <<= Byte.SIZE - stringByte.length();
            output[output.length - 1] = (byte) intByte;
        }
        return output;
    }

    /**
     * This method defines the size of output array
     *
     * @param table      coded sequence table
     * @param buffer     data buffer with not encoded data
     * @param lastBuffer is buffer last
     * @return size of array to store the buffer
     */
    private int determineOutputArraySize(Map<Byte, String> table, byte[] buffer, boolean lastBuffer) {
        double counter = 0;
        for (byte b : buffer) {
            counter += table.get(b).length();
        }
        return (int) (!lastBuffer ? Math.floor((counter + codedByteBuffer.length()) / Byte.SIZE) :
                Math.ceil((counter + codedByteBuffer.length()) / Byte.SIZE));
    }
}




