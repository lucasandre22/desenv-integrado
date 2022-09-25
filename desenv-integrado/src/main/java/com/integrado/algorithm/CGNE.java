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

    /*public static void model1() {
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
    }*/

    /*public static void model2Wikipedia() {
        CGNE teste = new CGNE();
        long startTime = System.currentTimeMillis();
        FloatMatrix b = teste.arrayG;
        FloatMatrix A = teste.matrixH;

        FloatMatrix x = FloatMatrix.zeros(27904, 1);
        FloatMatrix p = FloatMatrix.zeros(900, 1);
        FloatMatrix r = b.sub(A).mul(x);
        FloatMatrix rsold;
        p = r;
        rsold = r.transpose().mul(r);
        for(int i = 0; i < CONVERGENCE; i++) {
            FloatMatrix ap = A.mul(p);
            FloatMatrix rsnew;
            rsold.scalar();
            FloatMatrix alpha = rsold.div(p.transpose().mul(ap));
            x = x.add(p.mul(p));
            r = r.sub(alpha).mul(ap);
            rsnew = r.transpose().mul(r);
            //if(error)
            //break;
            p = r.add(rsnew.div(rsold)).mul(p);
            rsold = rsnew;
        }

        System.out.println("Time to complete: " + (System.currentTimeMillis() - startTime));
        printMatrix(x);
        //Transform array f to an image.
    }*/

    public static void model2() {
        CGNE teste = new CGNE();

        FloatMatrix f = FloatMatrix.zeros(1, 30*30);
        FloatMatrix fa = FloatMatrix.zeros(30*30, 1);
        FloatMatrix to_be_multiplied = FloatMatrix.zeros(27904, 1);
        teste.matrixH.mmuli(fa, to_be_multiplied);
        FloatMatrix r = teste.arrayG.sub(to_be_multiplied);
        FloatMatrix p = teste.matrixHTranspose.mmul(r);
        FloatMatrix r_next;
        long startTime = System.currentTimeMillis();

        /*for(int i = 0; i < CONVERGENCE; i++) {
            float alpha = CGNE.calculateAlpha(r, p);
            f = f.add(p.mmul(alpha));
            r_next = r.sub(teste.matrixH.mmul(alpha).mmul(p));
            float beta = CGNE.divide(r_next, r);
            p = teste.matrixHTranspose.mmul(r_next).add(p.mmul(beta));
        }*/
        for(int i = 0; i < CONVERGENCE; i++) {
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
    }

    /*public static void model2() {
        CGNE teste = new CGNE();
        System.out.println("arrayg: " + teste.arrayG.rows + " " + teste.arrayG.columns);
        System.out.println("matrixH: " + teste.matrixH.rows + " " + teste.matrixH.columns);

        FloatMatrix f = FloatMatrix.zeros(1, Constants.MODEL_2_N*Constants.MODEL_2_S);
        FloatMatrix fa = FloatMatrix.zeros(30*30, 1);
        teste.matrixH.mmuli(fa, f);
        //fa.mmuli(teste.matrixH, f);
        System.out.println("fa: " + fa.rows + " " + fa.columns);
        System.out.println("f: " + f.rows + " " + f.columns);
        FloatMatrix r = teste.arrayG.sub(f);
        FloatMatrix p = teste.matrixH.transpose().mmul(r);
        FloatMatrix r_next;
        long startTime = System.currentTimeMillis();

        for(int i = 0; i < CONVERGENCE; i++) {
            float alpha = division(r, p);
            System.out.println("p: " + p.mmul(alpha).rows + " " + p.mmul(alpha).columns);
            //f.addColumnVector(r_next);
            p.mmul(alpha);
            f = f.add(p.mmul(alpha));
            r_next = r.sub(teste.matrixH.mmul(alpha).mmul(p));
            float beta = division(r_next, r);
            //beta = r_next.transpose().mul(r_next).div(r.transpose().mul(r));
            p = teste.matrixH.mmul(r_next).add(p.mmul(beta));
        }

        System.out.println("Time to complete: " + (System.currentTimeMillis() - startTime));
    }*/

    private static float divide(FloatMatrix r, FloatMatrix p)
    {
        FloatMatrix rTransposed = r.transpose().mmul(r);

        FloatMatrix pTransposed = p.transpose().mmul(p);

        //Wrong I guess
        return rTransposed.get(0,0) / pTransposed.get(0,0);
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

    /**
     * TODO
     * @return
     */
    public static boolean verifyError(FloatMatrix r, FloatMatrix r_next) {
        System.out.println("ERROR: " + Math.abs(r_next.norm2() - r.norm2()));
        return Math.abs(r_next.norm2() - r.norm2()) < Constants.ERROR;
    }

    public static void main(String[] args) {
        CGNE a = new CGNE();
        model2();
    }
}
