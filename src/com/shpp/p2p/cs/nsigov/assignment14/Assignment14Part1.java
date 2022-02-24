package com.shpp.p2p.cs.nsigov.assignment14;

/**
 * Archiver program. Can unpack and archive files
 * Uses a simple data compression technique - code sequence constant length
 * On small amounts of data or files that use all 256 code combinations,
 * the size of the archived file will be larger than the initial one due to
 * the addition of service information
 */
public class Assignment14Part1 {

    /**
     * The main method calculates the execution time of the program and creates an object of the class ArchiveManager
     *
     * @param args Arguments from command line
     */
    public static void main(String[] args) {
        long startTime = System.nanoTime();
        ArchiveManager archiveManager = new ArchiveManager(args);
        archiveManager.chooseAction();
        long finishTime = System.nanoTime();
        System.out.println("Time: " + ((double) (finishTime - startTime) / 1_000_000_000) + " sec");
    }
}
