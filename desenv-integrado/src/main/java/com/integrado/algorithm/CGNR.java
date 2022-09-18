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

    public static void main(String[] args) {
        CGNR teste = new CGNR();
        FloatMatrix f = FloatMatrix.zeros(Constants.MODEL_2_S, Constants.MODEL_2_N);
        FloatMatrix r = teste.arrayG.sub(teste.matrixH.mul(f));
        FloatMatrix p = teste.matrixH.transpose().mul(r);
        FloatMatrix alpha, beta, r_next;
        long startTime = System.currentTimeMillis();

        for(int i = 0; i < CONVERGENCE; i++) {
            alpha = r.transpose().mul(r).div(p.transpose().mul(p));
            f = f.add(alpha.mul(p));
            r_next = r.sub(alpha.mul(teste.matrixH).mul(p));
            beta = r_next.transpose().mul(r_next).div(r.transpose().mul(r));
            p = teste.matrixH.mul(r_next).add(beta.mul(p));
        }
        System.out.println("Time to complete: " + (System.currentTimeMillis() - startTime));
    }
}
