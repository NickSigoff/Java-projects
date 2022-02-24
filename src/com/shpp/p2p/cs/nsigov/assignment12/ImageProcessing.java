package com.shpp.p2p.cs.nsigov.assignment12;

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
                    depthFirstSearch(i, j);
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
                // DIFFERENCE_BETWEEN_COLORS * 3 - multiples by 3 because three primary colors
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
     * The method determines the background color.
     * The method uses a loop to count the number of different pixels
     * around the perimeter of the picture, the color of more
     * pixels will be the background color
     *
     * @return Color of background
     */
    private Color determineBackgroundColor() {
        int counterFirstColor = 0;
        int counterSecondColor = 0;
        Color anotherColor = new Color(0, true);

        // takes the first pixel as the basis for the background
        Color firstColor = new Color(image.getRGB(0, 0), true);
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                if (i == 0 || i == image.getHeight() - 1 || j == 0 || j == image.getWidth() - 1) {
                    Color secondColor = new Color(image.getRGB(j, i));
                    //Checking if a color secondColor is a background or silhouette color
                    if (checkColor(firstColor, secondColor)) {
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
     * This method determines is there the significant difference between two colors to consider
     * them as a background color and  a silhouette color
     *
     * @param firstColor first Color
     * @param secondColor second Color
     * @return true - the difference between the colors is not significant false - significant
     */
    private boolean checkColor(Color firstColor, Color secondColor) {
        return firstColor.getRed() - DIFFERENCE_BETWEEN_COLORS < 0 ?
                firstColor.getRed() + DIFFERENCE_BETWEEN_COLORS > secondColor.getRed() :
                firstColor.getRed() - DIFFERENCE_BETWEEN_COLORS < secondColor.getRed() ||
                        firstColor.getGreen() - DIFFERENCE_BETWEEN_COLORS < 0 ?
                        firstColor.getGreen() + DIFFERENCE_BETWEEN_COLORS > secondColor.getGreen() :
                        firstColor.getGreen() - DIFFERENCE_BETWEEN_COLORS < secondColor.getGreen() ||
                                firstColor.getBlue() - DIFFERENCE_BETWEEN_COLORS < 0 ?
                                firstColor.getBlue() + DIFFERENCE_BETWEEN_COLORS > secondColor.getBlue() :
                                firstColor.getBlue() - DIFFERENCE_BETWEEN_COLORS < secondColor.getBlue();

    }

    /**
     * Recursive function changes the values of true to false within one silhouette,
     * fills in the variable of the number of pixels in the silhouette
     *
     * @param i row number
     * @param j column number
     */
    private void depthFirstSearch(int i, int j) {
        booleanImage[i][j] = false;
        counterPixels++;
        if (booleanImage[i == 0 ? i : i - 1][j]) { // checks the left pixel
            depthFirstSearch(i == 0 ? i : i - 1, j);
        }
        if (booleanImage[i == booleanImage.length - 1 ? i : i + 1][j]) { // checks the right pixel
            depthFirstSearch(i == booleanImage.length - 1 ? i : i + 1, j);
        }
        if (booleanImage[i][j == 0 ? j : j - 1]) { // checks the top pixel
            depthFirstSearch(i, j == 0 ? j : j - 1);
        }
        if (booleanImage[i][j == booleanImage[i].length - 1 ? j : j + 1]) { // checks the bottom pixel
            depthFirstSearch(i, j == booleanImage[i].length - 1 ? j : j + 1);
        }
    }
}


