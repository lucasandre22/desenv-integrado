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
        FloatMatrix f = FloatMatrix.zeros(1, 30*30);
        FloatMatrix r = arrayG;
        FloatMatrix p = matrixH.transpose().mmul(r);
        FloatMatrix r_next;

        int i = 0;
        float r_dot = 0;
        float new_r_dot = 0;
        float alpha = 0;
        float beta = 0;

        long startTime = System.currentTimeMillis();
        for(i = 1; i < Constants.CONVERGENCE; i++) {
            //calculate r_dot only first time, it will be already calculated in next iterations
            if(i == 1)
                r_dot = r.dot(r);
            alpha = r_dot/p.dot(p);
            f = f.add(p.mmul(alpha));
            r_next = r.sub(matrixH.mmul(alpha).mmul(p));
            if(verifyError(r, r_next)) {
                System.out.println("Error achieved!");
                break;
            }
            new_r_dot = r_next.dot(r_next);
            beta = new_r_dot/r_dot;
            r_dot = new_r_dot;
            p = matrixH.transpose().mmul(r_next).add(p.mmul(beta));
            r = r_next;
        }

        System.out.println("Time to complete: " + (System.currentTimeMillis() - startTime));
        return new AlgorithmOutput(f, i, (System.currentTimeMillis() - startTime));
    }

    //test
    public static void main(String[] args) {
        FloatMatrix arrayG = CsvParser.readFloatMatrixFromCsvFile(
                Constants.PATH_TO_MODEL_2_MATRIXES + Constants.MODEL_2_G_MATRIX_2);
        FloatMatrix matrixH = CsvParser.readFloatMatrixFromCsvFile(
                Constants.PATH_TO_MODEL_2_MATRIXES + Constants.MODEL_2_H_MATRIX);
        Algorithm cgne = new CGNE();
        AlgorithmOutput output = cgne.run(matrixH, arrayG);
        Image.saveFloatMatrixToImage(output.getOutputMatrix(), 30, 30, "cgne");
    }
}
