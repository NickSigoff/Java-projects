package com.shpp.p2p.cs.nsigov.assignment15;

/**
 * Archiver program. Can unpack and archive files
 * Uses the Huffman method to compress data.
 * The method of transferring the tree in compressed files is used
 * By adding service information, small files can be smaller than them archive
 */
public class Assignment15Part1 {

    /**
     * The main method calculates the execution time of the program and creates an object of the class ArchiveManager
     *
     * @param args Arguments from command line
     */
    public static void main(String[] args) {
        long startTime = System.nanoTime();
        FileNameManager fileNameManager = new FileNameManager(args);
        fileNameManager.chooseAction();
        long finishTime = System.nanoTime();
        System.out.println("Time: " + ((double) (finishTime - startTime) / 1_000_000_000) + " sec");
    }
}