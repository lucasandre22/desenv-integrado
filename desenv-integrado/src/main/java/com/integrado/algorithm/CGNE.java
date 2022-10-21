package com.integrado.algorithm;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    public AlgorithmOutput run(FloatMatrix matrixH, FloatMatrix arrayG, Model model) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");  
        LocalDateTime start = LocalDateTime.now();

        //define output image length by model
        int outputImageLength = model == Model.one ? 60 : 30;
        FloatMatrix f = FloatMatrix.zeros(1, outputImageLength*outputImageLength);
        FloatMatrix r = arrayG;
        FloatMatrix p = matrixH.transpose().mmul(r);
        FloatMatrix r_next;

        int i = 1;
        float r_dot = r.dot(r);
        float new_r_dot = 0;
        float alpha = 0;
        float beta = 0;

        long startTime = System.currentTimeMillis();
        for(; i < Constants.CONVERGENCE; i++) {
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
        
        return new AlgorithmOutput(f, AlgorithmType.CGNE, outputImageLength, 
                outputImageLength*outputImageLength, dateFormatter.format(start), 
                timeFormatter.format(start), timeFormatter.format(LocalDateTime.now()),
                i, (System.currentTimeMillis() - startTime));
    }

    public static void main(String[] args) {
        FloatMatrix arrayG = CsvParser.readFloatMatrixFromCsvFile(Constants.MODEL_1_G_MATRIX);
        FloatMatrix matrixH = CsvParser.readFloatMatrixFromCsvFile(Constants.MODEL_1_H_MATRIX);
        Algorithm cgne = new CGNE();
        AlgorithmOutput output = cgne.run(matrixH, arrayG, Model.one);
        Image.generateImageOutput(output, "cgne");
    }
}
