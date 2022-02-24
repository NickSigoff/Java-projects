package com.shpp.p2p.cs.nsigov.assignment17.assignment13;

import com.shpp.p2p.cs.nsigov.assignment17.assignment16.MyQueue;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.Color;


/**
 * Image processing class.
 * The main principle is to read the image, based on it, make a two-dimensional array,
 * where true is part of the object, false is part of the background. Using a recursive function,
 * the number of pixels of the object is counted, their value is replaced with false.
 * With the help of a loop, each pixel is traversed, after which the program displays the number of objects
 * Also added Breadth First Search method
 */
public class ImageProcessing {

    /**
     * Difference between silhouettes color and background color.
     * If the silhouettes are close, the constant should be greater than if the silhouettes are far away.
     */
    private static final int DIFFERENCE_BETWEEN_COLORS = 150;

    /**
     * Constant of the minimum number of pixels so that they are considered as a separate silhouette
     */
    private static final int MIN_PIXEL_IN_SILHOUETTE = 30;

    /**
     * Variable for counting the number of pixels in a silhouette
     */
    private static int counterPixels = 0;

    /**
     * The image received at the input of the program
     */
    private BufferedImage image;

    /**
     * A two-dimensional array contains the transformed image,
     * where true is the silhouette pixel, false is the background pixel
     */
    private boolean[][] booleanImage;

    /**
     * Class constructor. Processes input and defines a class field image
     *
     * @param s arguments from command line
     */
    ImageProcessing(String[] s) {
        try {
            image = ImageIO.read(new File(s.length == 0 ? "test.jpg" : s[0]));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * The main logic of the program checks if the value is found is true calls the recursive function depthFirstSearch
     *
     * @return Number of silhouettes
     */
    int findSilhouettes() {
        fillArray();
        int resultCounter = 0;
        for (int i = 0; i < booleanImage.length; i++) {
            for (int j = 0; j < booleanImage[i].length; j++) {
                if (booleanImage[i][j]) {
                    breadthFirstSearch(i, j);
                    if (counterPixels > MIN_PIXEL_IN_SILHOUETTE) {
                        resultCounter++;
                    }
                    counterPixels = 0;
                }
            }
        }
        return resultCounter;
    }

    /**
     * Method fills booleanImage by meanings, where true is the silhouette pixel, false is the background pixel
     */
    private void fillArray() {
        booleanImage = new boolean[image.getWidth()][image.getHeight()];
        Color backgroundColor = determineBackgroundColor();
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                Color silhouetteColor = new Color(image.getRGB(j, i), true);
                if (backgroundColor.getBlue() + backgroundColor.getRed() + backgroundColor.getGreen() > DIFFERENCE_BETWEEN_COLORS * 3) {
                    // light background
                    booleanImage[j][i] = backgroundColor.getRed() - DIFFERENCE_BETWEEN_COLORS > silhouetteColor.getRed()
                            || backgroundColor.getGreen() - DIFFERENCE_BETWEEN_COLORS > silhouetteColor.getGreen()
                            || backgroundColor.getBlue() - DIFFERENCE_BETWEEN_COLORS > silhouetteColor.getBlue()
                            || backgroundColor.getAlpha() - DIFFERENCE_BETWEEN_COLORS > silhouetteColor.getAlpha();
                } else {
                    // dark background
                    booleanImage[j][i] = backgroundColor.getRed() + DIFFERENCE_BETWEEN_COLORS < silhouetteColor.getRed()
                            || backgroundColor.getGreen() + DIFFERENCE_BETWEEN_COLORS < silhouetteColor.getGreen()
                            || backgroundColor.getBlue() + DIFFERENCE_BETWEEN_COLORS < silhouetteColor.getBlue()
                            || backgroundColor.getAlpha() + DIFFERENCE_BETWEEN_COLORS < silhouetteColor.getAlpha();
                }
            }
        }
    }

    /**
     * The method determines the background color by walking along the perimeter of the picture
     * and counting the pixels. Larger number of pixels background color
     *
     * @return Color of background
     */
    private Color determineBackgroundColor() {
        int counterFirstColor = 0;
        int counterSecondColor = 0;
        Color anotherColor = new Color(0, true);
        Color firstColor = new Color(image.getRGB(0, 0), true);// takes the first pixel as the basis for the background
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                if (i == 0 || i == image.getHeight() - 1 || j == 0 || j == image.getWidth() - 1) {
                    Color secondColor = new Color(image.getRGB(j, i));
                    if (firstColor.getRed() - DIFFERENCE_BETWEEN_COLORS < 0 ?
                            firstColor.getRed() + DIFFERENCE_BETWEEN_COLORS > secondColor.getRed() :
                            firstColor.getRed() - DIFFERENCE_BETWEEN_COLORS < secondColor.getRed() ||
                                    firstColor.getGreen() - DIFFERENCE_BETWEEN_COLORS < 0 ?
                                    firstColor.getGreen() + DIFFERENCE_BETWEEN_COLORS > secondColor.getGreen() :
                                    firstColor.getGreen() - DIFFERENCE_BETWEEN_COLORS < secondColor.getGreen() ||
                                            firstColor.getBlue() - DIFFERENCE_BETWEEN_COLORS < 0 ?
                                            firstColor.getBlue() + DIFFERENCE_BETWEEN_COLORS > secondColor.getBlue() :
                                            firstColor.getBlue() - DIFFERENCE_BETWEEN_COLORS < secondColor.getBlue()
                    ) {
                        counterFirstColor++;
                    } else {
                        counterSecondColor++;
                        anotherColor = secondColor;
                    }
                }
            }
        }
        return counterFirstColor > counterSecondColor ? firstColor : anotherColor;
    }

    /**
     * Breadth first search method. Runs when the main loop finds pixel silhouette flowers
     *
     * @param i row number
     * @param j column number
     */
    private void breadthFirstSearch(int i, int j) {
        MyQueue<String> queue = new MyQueue<>();
        booleanImage[i][j] = false;
        checkPixels(i, j, queue);
        while (!queue.isEmpty()) {
            counterPixels++;
            String coordinates = queue.poll();
            String[] words = coordinates.split("\\|");
            i = Integer.parseInt(words[0]);
            j = Integer.parseInt(words[1]);
            checkPixels(i, j, queue);
        }
    }

    /**
     * The method checks the top, bottom, right, left pixels for color.
     * If the color matches the silhouette color, the pixel with these coordinates is added to the queue.
     * The coordinates are stored in the queue, separated by a character "|" as a string
     *
     * @param i row number
     * @param j column number
     * @param queue a queue containing pixel coordinates with silhouette color
     */
    private void checkPixels(int i, int j, MyQueue<String> queue) {
        if (booleanImage[i == 0 ? i : i - 1][j]) { // checks the left pixel
            booleanImage[i == 0 ? i : i - 1][j] = false;
            queue.add((i == 0 ? i : i - 1) + "|" + j);
        }
        if (booleanImage[i == booleanImage.length - 1 ? i : i + 1][j]) { // checks the right pixel
            booleanImage[i == booleanImage.length - 1 ? i : i + 1][j] = false;
            queue.add((i == booleanImage.length - 1 ? i : i + 1) + "|" + j);
        }
        if (booleanImage[i][j == 0 ? j : j - 1]) { // checks the top pixel
            booleanImage[i][j == 0 ? j : j - 1] = false;
            queue.add(i + "|" + (j == 0 ? j : j - 1));
        }
        if (booleanImage[i][j == booleanImage[i].length - 1 ? j : j + 1]) { // checks the bottom pixel
            booleanImage[i][j == booleanImage[i].length - 1 ? j : j + 1] = false;
            queue.add(i + "|" + (j == booleanImage[i].length - 1 ? j : j + 1));
        }
    }
}