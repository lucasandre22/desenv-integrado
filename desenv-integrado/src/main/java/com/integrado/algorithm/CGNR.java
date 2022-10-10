package com.integrado.algorithm;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.jblas.FloatMatrix;

import com.integrado.algorithm.Algorithm.AlgorithmType;
import com.integrado.model.Image;
import com.integrado.util.Constants;
import com.integrado.util.CsvParser;

import lombok.Data;


/**
 * 
 * Conjugate Gradient Method Normal Residual (Saad2003, p. 266)
 *
 */
@Data
public class CGNR implements Algorithm {
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
        FloatMatrix z = matrixH.transpose().mmul(r);
        FloatMatrix p = z;

        FloatMatrix r_next;
        FloatMatrix z_anterior = z;

        long startTime = System.currentTimeMillis();
        int i = 1;
        for(i = 1; i < Constants.CONVERGENCE; i++) {
            FloatMatrix w = matrixH.mmul(p);

            float alpha = (z.norm2() * z.norm2()) / (w.norm2() * w.norm2());

            f = f.add(p.mul(alpha));
            r_next = r.sub(w.mul(alpha));

            if(verifyError(r, r_next)) {
                System.out.println("Error achieved!");
                break;
            }

            z = matrixH.transpose().mmul(r_next);

            float beta = (z.norm2() * z.norm2()) / (z_anterior.norm2() * z_anterior.norm2());
            p = z.add(p.mul(beta));

            r = r_next;
            z_anterior = z;
        }
        LocalDateTime finishTime = LocalDateTime.now();

        System.out.println("Time to complete: " + (System.currentTimeMillis() - startTime));
        return new AlgorithmOutput(f, AlgorithmType.CGNR, outputImageLength, 
                outputImageLength*outputImageLength, dateFormatter.format(start), 
                timeFormatter.format(start), timeFormatter.format(LocalDateTime.now()),
                i, (System.currentTimeMillis() - startTime));
    }


    public static void main(String[] args) {
        FloatMatrix arrayG = CsvParser.readFloatMatrixFromCsvFile(
                Constants.MODEL_1_G_MATRIX);
        FloatMatrix matrixH = CsvParser.readFloatMatrixFromCsvFile(
                Constants.MODEL_1_H_MATRIX);
        Algorithm cgnr = new CGNR();
        AlgorithmOutput output = cgnr.run(matrixH, arrayG, Model.one);
        Image.generateImageOutput(output, "cgnr");
    }
}
