package controller;

import view.FilterFrame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FilterController {

    /** Входное изображение */
    private static BufferedImage image;

    /** Список изображений */
    private static ArrayList<BufferedImage> images;

    /** Путь к изображению */
    private static File FILE = new File("src//main//resources//Lenna.png");

    /** Тип сохраняемого изображения */
    private static String TYPE = "png";

    /** Путь сохранения изображений */
    private static String PATH = "outputImages//";

    /** Размер матрицы */
    private static int MATRIX_SIZE = 5;

    /** Значение для матрицы Гаусса */
    private static float VALUE = 1.0f / 5.0f;

    /** Размытие изображения */
    private static final double[][] FILTER_BLUR    = new double[][]{
            { 0.000789, 0.006581, 0.013347, 0.006581, 0.000789 },
            { 0.006581, 0.054901, 0.111345, 0.054901, 0.006581 },
            { 0.013347, 0.111345, 0.225821, 0.111345, 0.013347 },
            { 0.006581, 0.054901, 0.111345, 0.054901, 0.006581 },
            { 0.000789, 0.006581, 0.013347, 0.006581, 0.000789 } };

    /** Увеличение четкости */
    private static final double[][] FILTER_SHARPEN = new double[][]{
            {-1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1},
            {-1, -1, 25, -1, -1},
            {-1, -1, -1, -1, -1},
            {-1, -1, -1 ,-1, -1}};

    /** Увеличение четкости на краях */
    private static final double[][] FILTER_EMBOSS  = new double[][]{
            { 1,  0,  0,  0,  0 },
            { 0,  1,  0,  0,  0 },
            { 0,  0, -1,  0,  0 },
            { 0,  0,  0, -1,  0 },
            { 0,  0,  0,  0, -1 }};

    /** Переменная для создания монохромного изображения */
    private static final int MONOCHROME = 368;

    /** Умножение изображения на  матрицу */
    public static BufferedImage process(BufferedImage inputImage, double[][] filter) {
        BufferedImage output = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(),
                BufferedImage.TYPE_INT_ARGB);

        for (int i = 2; i < inputImage.getWidth() - 2; ++i) {
            for (int j = 2; j < inputImage.getHeight() - 2; ++j) {
                Color[][] outputMatrix = getMatrixByCenter(inputImage, i, j);
                Color outputColor = multiply(outputMatrix, filter);
                output.setRGB(i, j, outputColor.getRGB());
            }
        }
        return output;
    }

    /** Создание монохромного изображения */
    public static BufferedImage monochrome(BufferedImage inputImage) {
        BufferedImage outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(),
                BufferedImage.TYPE_INT_ARGB);

        for (int i = 0; i < inputImage.getWidth(); ++i) {
            for (int j = 0; j < inputImage.getHeight(); ++j) {
                Color outputColor = new Color(inputImage.getRGB(i, j));

                if (outputColor.getRed() + outputColor.getGreen() + outputColor.getBlue() > MONOCHROME)
                    outputColor = new Color(255, 255, 255);
                else
                    outputColor = new Color(0, 0, 0);
                outputImage.setRGB(i, j, outputColor.getRGB());
            }
        }
        return  outputImage;
    }

    /** Получение матрицы цветов */
    private static Color[][] getMatrixByCenter(BufferedImage source, int i, int j) {
        Color[][] imageMatrix = new Color[5][5];
        for (int x = -2; x < 3; ++x) {
            for (int y = -2; y < 3; ++y) {
                imageMatrix[x + 2][y + 2] = new Color(source.getRGB(i + x, j + y));
            }
        }
        return imageMatrix;
    }

    /** Умножение изображения на матрицу */
    private static Color multiply(Color[][] imageMatrix, double[][] filter) {
        double red = 0;
        for (int col = 0; col < MATRIX_SIZE; ++col) {
            for (int row = 0; row < MATRIX_SIZE; ++row) {
                red += imageMatrix[col][row].getRed() * filter[row][col];
            }
        }

        double green = 0;
        for (int col = 0; col < MATRIX_SIZE; ++col) {
            for (int row = 0; row < MATRIX_SIZE; ++row) {
                green += imageMatrix[col][row].getGreen() * filter[row][col];
            }
        }

        double blue = 0;
        for (int col = 0; col < MATRIX_SIZE; ++col) {
            for (int row = 0; row < MATRIX_SIZE; ++row) {
                blue += imageMatrix[col][row].getBlue() * filter[row][col];
            }
        }
        return new Color((int) red, (int) green, (int) blue);
    }

    /** Загрузка изображения из файла */
    private static void read(File file) {
        try {
            image = ImageIO.read(file);
            images.add(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Сохранение изображения в формате PNG */
    private static void save(BufferedImage image, String name) {
        try {
            ImageIO.write(image, TYPE, new File(PATH + "filter." + name + "." + TYPE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        images = new ArrayList<BufferedImage>();

        read(FILE);
        BufferedImage blurred    = process(image, FILTER_BLUR);
        save(blurred, "blurred");

        BufferedImage monochrome = monochrome(image);
        save(monochrome, "monochrome");

        BufferedImage embossed   = process(image, FILTER_EMBOSS);
        save(embossed, "embossed");

        BufferedImage sharpen    = process(image, FILTER_SHARPEN);
        save(sharpen, "sharpen");

        new FilterFrame(images).setVisible(true);
    }

}
