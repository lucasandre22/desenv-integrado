package com.integrado.util;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.jblas.FloatMatrix;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class CsvParser {
    
    public static double[][] readDoubleMatrixFromCsvFile(String matrixName) {
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
        return matrixDouble;
    }

    public static float[][] readFloatMatrixFromCsvFile(String matrixName) {
        List<float[]> matrixLines = new ArrayList<float[]>();
        float[][] matrixDouble;

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
        return matrixDouble;
    }

    public static FloatMatrix readFloatMatrixFromCsvFile(String matrixName, char separator) {
        FloatMatrix matrix = null;
        List<float[]> matrixLines = new ArrayList<float[]>();
        
        try {
            CSVParser parser = new CSVParserBuilder().withSeparator(separator).build();
            CSVReader csvReader = new CSVReaderBuilder(new FileReader(new File(matrixName)))
                                  .withCSVParser(parser)
                                  .build();

            //Utiliza matriz pois o jblas só constroi FloatMatrix com uma matriz (1,n) 
            //a partir de uma matriz
            String[] values;
            int j = 0;
            while ((values = csvReader.readNext()) != null) {
                //Só sabemos quantas colunas os arquivo têm depois de lê-lo, por isso declarar aqui
                float[] matrixLine = new float[values.length];
                
                int i = 0;
                for(String value : values) {
                    matrixLine[i++] = Double.valueOf(value).floatValue();
                }
                matrixLines.add(matrixLine);
                //System.out.println("Lido linha: " + j++);
            }
        } catch(Exception e) {
            System.err.println(e);
        }
        
        int i=0;
        float[][] grouped = new float[matrixLines.size()][matrixLines.get(0).length];
        for(float[] line : matrixLines) {
            grouped[i++] = line;
        }
        
        matrix = new FloatMatrix(grouped);
        return matrix;
    }
}
