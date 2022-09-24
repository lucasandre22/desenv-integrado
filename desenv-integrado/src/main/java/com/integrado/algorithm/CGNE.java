package com.integrado.algorithm;

import org.jblas.DoubleMatrix;
import org.jblas.FloatMatrix;

import com.integrado.util.Constants;
import com.integrado.util.CsvParser;

import lombok.Data;

/**
 * 
 * Conjugate Gradient Method Normal Error
 *
 */
@Data
public class CGNE implements Algorithm {

    private final static int CONVERGENCE = 1;

    /*private FloatMatrix arrayG = new FloatMatrix(CsvParser.readFloatMatrixFromCsvFile(
            Constants.PATH_TO_MODEL_1_MATRIXES + Constants.MODEL_1_G_MATRIX));
    private FloatMatrix matrixH = new FloatMatrix(CsvParser.readFloatMatrixFromCsvFile(
            Constants.PATH_TO_MODEL_1_MATRIXES + Constants.MODEL_1_H_MATRIX));*/
    private DoubleMatrix arrayG = new DoubleMatrix(CsvParser.readDoubleMatrixFromCsvFile(
            Constants.PATH_TO_MODEL_2_MATRIXES + Constants.MODEL_2_G_MATRIX));
    private DoubleMatrix matrixH = new DoubleMatrix(CsvParser.readDoubleMatrixFromCsvFile(
            Constants.PATH_TO_MODEL_2_MATRIXES + Constants.MODEL_2_H_MATRIX));

    public static void model1() {
        CGNE teste = new CGNE();
        System.out.println(teste.arrayG.length);
        System.out.println(teste.matrixH.length);
        System.out.println(teste.matrixH.length);

        FloatMatrix f = FloatMatrix.zeros(Constants.MODEL_1_S, Constants.MODEL_1_N);
        System.out.println(f.length);
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

    public static void model2() {
        CGNE teste = new CGNE();
        System.out.println(teste.arrayG.get(97));
        System.out.println(teste.arrayG.get(97)/2);

        DoubleMatrix f = DoubleMatrix.zeros(Constants.MODEL_2_S, Constants.MODEL_2_N);
        DoubleMatrix r = teste.arrayG.sub(teste.matrixH.mul(f));
        DoubleMatrix p = teste.matrixH.transpose().mul(r);
        DoubleMatrix alpha, beta, r_next;
        long startTime = System.currentTimeMillis();

        for(int i = 0; i < CONVERGENCE; i++) {
            alpha = r.transpose().mul(r).div(p.transpose().mul(p));
            printMatrix(alpha);
            f = f.add(alpha.mul(p));
            r_next = r.sub(alpha.mul(teste.matrixH).mul(p));
            beta = r_next.transpose().mul(r_next).div(r.transpose().mul(r));
            p = teste.matrixH.mul(r_next).add(beta.mul(p));
        }

        System.out.println("Time to complete: " + (System.currentTimeMillis() - startTime));
        //TODO
        //Transform array f to an image.
    }
    
    public static void printMatrix(DoubleMatrix matrix) {
        for(int i = 0; i < matrix.columns*matrix.rows; i++) {
            System.out.println(matrix.get(i));
        }
    }

    public static void main(String[] args) {
        model2();
    }
}
