package controller;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FilterController {

    private BufferedImage image;
    private double[][]    gaussMatrix   = new double[][]{
            { 0.000789, 0.006581, 0.013347, 0.006581, 0.000789 },
            { 0.006581, 0.054901, 0.111345, 0.054901, 0.006581 },
            { 0.013347, 0.111345, 0.225821, 0.111345, 0.013347 },
            { 0.006581, 0.054901, 0.111345, 0.054901, 0.006581 },
            { 0.000789, 0.006581, 0.013347, 0.006581, 0.000789 }};
    private double[][]    embossMatrix  = new double[][]{
            {-1,-1,-1,-1, 0 },
            {-1,-1,-1, 0, 1 },
            {-1,-1, 0, 1, 1 },
            {-1, 0, 1, 1, 1 },
            { 0, 1, 1, 1, 1 }};
    private double[][]    sharpenMatrix   = new double[][]{
            {-1,-1,-1,-1,-1 },
            {-1, 2, 2, 2,-1 },
            {-1, 2, 8, 2,-1 },
            {-1, 2, 2, 2,-1 },
            {-1,-1,-1,-1,-1 }};

    public FilterController(BufferedImage inputImage) {
        this.image = inputImage;
    }

    public BufferedImage sharpenFilter() {

        return image;
    }

    public BufferedImage embossFilter() {

        return image;
    }

    public BufferedImage edgesFilter() {
        for (int i = 2; i < image.getWidth() - 2; i++) {
            for (int j = 2; j < image.getHeight() - 2; j++) {
                Color[][] imageMatrix = getMatrixByCenter(image, i, j);
                Color distColor = matrixMultiplication(imageMatrix, gaussMatrix);
                image.setRGB(i, j, distColor.getRGB());
            }
        }
        return image;
    }

    private Color[][] getMatrixByCenter(BufferedImage source, int i, int j) {
        Color[][] imageMatrix = new Color[5][5];
        for (int x = -2; x < 3; ++x) {
            for (int y = -2; y < 3; ++y) {
                imageMatrix[x + 2][y + 2] = new Color(source.getRGB(i + x, j + y));
            }
        }
        return imageMatrix;
    }

    private static Color matrixMultiplication(Color[][] imageMatrix, double[][] filterMatrix) {
        double red = 0;
        for (int col = 0; col < 5; col++) {
            for (int row = 0; row < 5; row++) {
                red += imageMatrix[col][row].getRed() * filterMatrix[row][col];
            }
        }

        double green = 0;
        for (int col = 0; col < 5; col++) {
            for (int row = 0; row < 5; row++) {
                green += imageMatrix[col][row].getGreen() * filterMatrix[row][col];
            }
        }

        double blue = 0;
        for (int col = 0; col < 5; col++) {
            for (int row = 0; row < 5; row++) {
                blue += imageMatrix[col][row].getBlue() * filterMatrix[row][col];
            }
        }
        return new Color((int) red, (int) green, (int) blue);
    }

}
