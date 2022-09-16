package com.integrado.util;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

public class CsvParser {
    
    public static double[][] readMatrixFromCsvFile(String matrixName) {
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
        System.out.println(matrixDouble[0][0]);
        return matrixDouble;
    }
}
