package com.integrado.algorithm;

import org.jblas.DoubleMatrix;

import com.integrado.util.CsvParser;

/**
 * 
 * Conjugate Gradient Method Normal Residual (Saad2003, p. 266)
 *
 */
public class CGNR implements Algorithm {

    private static final String PATH_TO_MATRIXES = "../modelo1/";
    private DoubleMatrix arrayG = new DoubleMatrix(CsvParser.readMatrixFromCsvFile(PATH_TO_MATRIXES + "g-30x30-1.csv"));
    private DoubleMatrix matrixH = new DoubleMatrix(CsvParser.readMatrixFromCsvFile(PATH_TO_MATRIXES + "H-2.csv"));

    public static void main(String[] args) {
        CGNR teste = new CGNR();
        
    }
}
