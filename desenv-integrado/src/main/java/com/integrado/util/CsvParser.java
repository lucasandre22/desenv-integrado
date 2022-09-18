package com.integrado.util;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

public class CsvParser {
    
    public static double[][] readDoubleMatrixFromCsvFile(String matrixName) {
        List<double[]> matrixLines = new ArrayList<double[]>();
        double[][] matrixDouble;

        try (CSVReader csvReader = new CSVReader(new FileReader(matrixName))) {
            for(String[] values = csvReader.readNext(); values != null; values = csvReader.readNext()) {
                values = values[0].split(";");
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
        return matrixDouble;
    }
    
    public static float[][] readFloatMatrixFromCsvFile(String matrixName) {
        List<float[]> matrixLines = new ArrayList<float[]>();
        float[][] matrixDouble;

        try (CSVReader csvReader = new CSVReader(new FileReader(matrixName))) {
            for(String[] values = csvReader.readNext(); values != null; values = csvReader.readNext()) {
                values = values[0].split(";");
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
        return matrixDouble;
    }
}
