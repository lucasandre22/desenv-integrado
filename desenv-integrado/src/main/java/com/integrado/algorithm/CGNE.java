package com.integrado.algorithm;

import org.jblas.FloatMatrix;

import com.integrado.model.Image;
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

    /**
     * 
     * @return AlgorithmOutput object.
     */
    public AlgorithmOutput run(FloatMatrix matrixH, FloatMatrix arrayG) {
        FloatMatrix matrixHTransposed = matrixH.transpose();
        FloatMatrix f = FloatMatrix.zeros(1, 30*30);
        FloatMatrix fa = FloatMatrix.zeros(30*30, 1);
        FloatMatrix to_be_multiplied = FloatMatrix.zeros(27904, 1);
        matrixH.mmuli(fa, to_be_multiplied);
        FloatMatrix r = arrayG;
        FloatMatrix p = matrixHTransposed.mmul(r);
        FloatMatrix r_next;
        long startTime = System.currentTimeMillis();
        int i = 0;

        for(i = 1; i < Constants.CONVERGENCE; i++) {
            float r_dot = r.dot(r);
            float alpha = r_dot/p.dot(p);
            f = f.add(p.mmul(alpha));
            r_next = r.sub(matrixH.mmul(alpha).mmul(p));
            if(verifyError(r, r_next)) {
                System.out.println("Error achieved!");
                break;
            }
            float beta = r_next.dot(r_next)/r_dot;
            p = matrixHTransposed.mmul(r_next).add(p.mmul(beta));
            r = r_next;
        }

        System.out.println("Time to complete: " + (System.currentTimeMillis() - startTime));
        return new AlgorithmOutput(f, i, (System.currentTimeMillis() - startTime));
    }

    public static void main(String[] args) {
        FloatMatrix arrayG = CsvParser.readFloatMatrixFromCsvFile(
                Constants.PATH_TO_MODEL_2_MATRIXES + Constants.MODEL_2_G_MATRIX);
        FloatMatrix matrixH = CsvParser.readFloatMatrixFromCsvFile(
                Constants.PATH_TO_MODEL_2_MATRIXES + Constants.MODEL_2_H_MATRIX);
        Algorithm cgnr = new CGNE();
        AlgorithmOutput output = cgnr.run(matrixH, arrayG);
        Image.saveFloatMatrixToImage(output.getOutputMatrix(), 30, 30, "cgne");
    }
}
