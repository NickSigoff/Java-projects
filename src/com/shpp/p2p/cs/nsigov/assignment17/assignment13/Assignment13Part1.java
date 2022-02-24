package com.shpp.p2p.cs.nsigov.assignment17.assignment13;

/**
 * The program counts the number of elements (silhouettes).
 * The program uses the depth-first search method
 */
public class Assignment13Part1 {
    public static void main(String[] args) {
        ImageProcessing imageProcessing = new ImageProcessing(args);
        System.out.println("Number of silhouettes = " + imageProcessing.findSilhouettes());
    }
}
