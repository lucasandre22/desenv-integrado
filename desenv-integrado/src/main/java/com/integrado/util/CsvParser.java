package com.integrado.util;

import java.io.FileReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.jblas.DoubleMatrix;
import org.jblas.FloatMatrix;

import com.opencsv.CSVReader;

public class CsvParser {

    public static DoubleMatrix readDoubleMatrixFromCsvFile(String matrixName) {
        List<double[]> matrixLines = new ArrayList<double[]>();
        double[][] matrixDouble;

        try (CSVReader csvReader = new CSVReader(new FileReader(matrixName))) {
            for(String[] values = csvReader.readNext(); values != null; values = csvReader.readNext()) {
                double[] matrixLine = new double[values.length];
                int i = 0;
                for(String value : values) {
                    matrixLine[i++] = Double.parseDouble(value);
                }
                matrixLines.add(matrixLine);
            }
        } catch(Exception e) {
            System.err.println(e);
        }
        matrixDouble = new double[matrixLines.size()][matrixLines.get(0).length];
        int i = 0;
        for(double[] line: matrixLines) {
            matrixDouble[i++] = line;
        }
        return new DoubleMatrix(matrixDouble);
    }

    public static FloatMatrix readFloatMatrixFromCsvFile(String matrixName) {
        List<float[]> matrixLines = new ArrayList<float[]>();
        float[][] matrixDouble;
        long start = System.currentTimeMillis();
        NumberFormat formatter = new DecimalFormat("#0.00000");

        try (CSVReader csvReader = new CSVReader(new FileReader(matrixName))) {
            for(String[] values = csvReader.readNext(); values != null; values = csvReader.readNext()) {
                float[] matrixLine = new float[values.length];
                int i = 0;
                for(String value : values) {
                    matrixLine[i++] = Float.parseFloat(value);
                }
                matrixLines.add(matrixLine);
            }
        } catch(Exception e) {
            System.err.println(e);
        }
        matrixDouble = new float[matrixLines.size()][matrixLines.get(0).length];
        int i = 0;
        for(float[] line: matrixLines) {
            matrixDouble[i++] = line;
        }

        System.out.print("Total time: " + formatter.format((System.currentTimeMillis() - start) / 1000d) + " seconds");
        return new FloatMatrix(matrixDouble);
    }

    public static void writeToCsv(FloatMatrix a) {

    }
}
