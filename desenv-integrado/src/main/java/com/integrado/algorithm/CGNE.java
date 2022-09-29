package com.integrado.algorithm;

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

    private final static int CONVERGENCE = 100;

    /*private FloatMatrix arrayG2 = CsvParser.readFloatMatrixFromCsvFile(
            Constants.PATH_TO_MODEL_1_MATRIXES + Constants.MODEL_1_G_MATRIX, ',');
    private FloatMatrix matrixH2 = CsvParser.readFloatMatrixFromCsvFile(
            Constants.PATH_TO_MODEL_1_MATRIXES + Constants.MODEL_1_H_MATRIX, ',');*/
    /*private FloatMatrix arrayG = new FloatMatrix(CsvParser.readFloatMatrixFromCsvFile(
            Constants.PATH_TO_MODEL_1_MATRIXES + Constants.MODEL_1_G_MATRIX));
    private FloatMatrix matrixH = new FloatMatrix(CsvParser.readFloatMatrixFromCsvFile(
            Constants.PATH_TO_MODEL_1_MATRIXES + Constants.MODEL_1_H_MATRIX));*/
    private FloatMatrix arrayG = new FloatMatrix(CsvParser.readFloatMatrixFromCsvFile(
            Constants.PATH_TO_MODEL_2_MATRIXES + Constants.MODEL_2_G_MATRIX));
    private FloatMatrix matrixH = new FloatMatrix(CsvParser.readFloatMatrixFromCsvFile(
            Constants.PATH_TO_MODEL_2_MATRIXES + Constants.MODEL_2_H_MATRIX));
    private FloatMatrix matrixHTranspose;
    
    public CGNE() {
        this.matrixHTranspose = matrixH.transpose();
    }

    /**
     * 
     * @return number of iterations that took until error.
     */
    public static int model2() {
        CGNE teste = new CGNE();

        FloatMatrix f = FloatMatrix.zeros(1, 30*30);
        FloatMatrix fa = FloatMatrix.zeros(30*30, 1);
        FloatMatrix to_be_multiplied = FloatMatrix.zeros(27904, 1);
        teste.matrixH.mmuli(fa, to_be_multiplied);
        FloatMatrix r = teste.arrayG.sub(to_be_multiplied);
        FloatMatrix p = teste.matrixHTranspose.mmul(r);
        FloatMatrix r_next;
        long startTime = System.currentTimeMillis();
        int i = 0;

        for(i = 0; i < CONVERGENCE; i++) {
            float r_dot = r.dot(r);
            float alpha = r_dot/p.dot(p);
            f = f.add(p.mmul(alpha));
            r_next = r.sub(teste.matrixH.mmul(alpha).mmul(p));
            if(verifyError(r, r_next)) {
                System.out.println("Error achieved!");
                break;
            }
            float beta = r_next.dot(r_next)/r_dot;
            p = teste.matrixHTranspose.mmul(r_next).add(p.mmul(beta));
        }

        printImage(f, 30);
        System.out.println("Time to complete: " + (System.currentTimeMillis() - startTime));
        return i;
    }

    public static boolean verifyError(FloatMatrix r, FloatMatrix r_next) {
        System.out.println("ERROR: " + Math.abs(r_next.norm2() - r.norm2()));
        return Math.abs(r_next.norm2() - r.norm2()) < Constants.ERROR;
    }

    public static void printMatrix(FloatMatrix matrix) {
        for(int i = 0; i < matrix.columns*matrix.rows; i++) {
            System.out.println(matrix.get(i));
        }
    }

    public static void printImage(FloatMatrix matrix, int columns) {
        for(int i = 0; i < matrix.columns*matrix.rows; i++) {
            System.out.print(matrix.get(i) + " ");
            if((i+1) % columns == 0) {
                System.out.print("\n");
            }
        }
    }

    public static void main(String[] args) {
        CGNE a = new CGNE();
        model2();
    }
}
