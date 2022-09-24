package com.integrado.algorithm;

import org.jblas.FloatMatrix;

import com.integrado.util.Constants;
import com.integrado.util.CsvParser;

/**
 * 
 * Conjugate Gradient Method Normal Residual (Saad2003, p. 266)
 *
 */
public class CGNR implements Algorithm {

    private final static int CONVERGENCE = 1000;

    private FloatMatrix arrayG = new FloatMatrix(CsvParser.readFloatMatrixFromCsvFile(
            Constants.PATH_TO_MODEL_2_MATRIXES + Constants.MODEL_2_G_MATRIX));
    private FloatMatrix matrixH = new FloatMatrix(CsvParser.readFloatMatrixFromCsvFile(
            Constants.PATH_TO_MODEL_2_MATRIXES + Constants.MODEL_2_H_MATRIX));


}
