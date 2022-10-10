package com.integrado.model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jblas.FloatMatrix;

import com.integrado.algorithm.AlgorithmOutput;

import lombok.Data;

@Data
public class Image {
    
    public static void generateImageOutput(AlgorithmOutput output, String username) {
        String filename = username + "_" + output.getAlgorithm() + "_" +
                              output.getDate() + "_" + output.getStartTime() + "_" +
                              output.getDate() + "_" + output.getEndTime() + "_" +
                              output.getPixelsLength() + "_" + output.getTotalIterations();

        Image.saveFloatMatrixToImage(output.getOutputMatrix(),
                output.getImageLength(), output.getImageLength(), filename);
    }

    public static void saveFloatMatrixToImage(FloatMatrix matrix, int width, int height, String filename) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        final float max = max_element(matrix.data, width*height);
        final float min = min_element(matrix.data, width*height);
        final float den = max - min;

        for (int i = 0; i < width*height; i++) {
            matrix.data[i] = (matrix.data[i] - min) / den;
        }

        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                Color color = new Color(matrix.data[j+(i*width)], matrix.data[j+(i*width)], matrix.data[j+(i*width)]);
                image.setRGB(i, j, color.getRGB());
            }
        }

        File file = new File("./" + filename + ".bmp");
        try {
            ImageIO.write(image, "bmp", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static float max_element(final float[] x, int n) {
        int r = 0;
        for (int i = 1; i < n; i++)
            if (x[i] > x[r])
                r = i;
        return x[r];
    }

    private static float min_element(final float[] x, int n) {
        int r = 0;
        for (int i = 1; i < n; i++)
            if (x[i] < x[r])
                r = i;
        return x[r];
    }

}
