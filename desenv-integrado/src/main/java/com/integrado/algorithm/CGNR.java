package com.integrado.algorithm;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicBoolean;

import org.jblas.FloatMatrix;

import com.integrado.algorithm.Algorithm.AlgorithmType;
import com.integrado.algorithm.Algorithm.Model;
import com.integrado.dto.AlgorithmInputDTO;
import com.integrado.model.Image;
import com.integrado.util.Constants;
import com.integrado.util.CsvParser;
import com.integrado.util.LoadMonitor;

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
    public AlgorithmOutput run(FloatMatrix arrayG, AlgorithmInputDTO algorithmInput) throws OutOfMemoryError {
        LocalDateTime start = LocalDateTime.now();
        Model model = algorithmInput.getModel();

        //define output image length by model
        int outputImageLength = model == Model.one ? 60 : 30;
        FloatMatrix f = FloatMatrix.zeros(1, outputImageLength*outputImageLength);
        FloatMatrix r = arrayG;
        FloatMatrix transpose = AlgorithmMatrixes.getMatrixH(model).transpose();
        FloatMatrix z = transpose.mmul(r);
        FloatMatrix p = z;

        FloatMatrix r_next;
        FloatMatrix z_anterior = z;

        LoadMonitor.increaseMemoryAvailable(algorithmInput.getModel());
        long startTime = System.currentTimeMillis();
        int i = 1;
        for(i = 1; i < Constants.CONVERGENCE; i++) {
            FloatMatrix w = AlgorithmMatrixes.getMatrixH(model).mmul(p);

            float alpha = (z.norm2() * z.norm2()) / (w.norm2() * w.norm2());

            f = f.add(p.mul(alpha));
            r_next = r.sub(w.mul(alpha));

            if(verifyError(r, r_next)) {
                //System.out.println("Error achieved!");
                break;
            }

            z = transpose.mmul(r_next);

            float beta = (z.norm2() * z.norm2()) / (z_anterior.norm2() * z_anterior.norm2());
            p = z.add(p.mul(beta));

            r = r_next;
            z_anterior = z;
            
        }
        z = null;
        r_next = null;
        z_anterior = null;
        r = null;
        p = null;
        arrayG = null;
        transpose = null;
        //call garbage collector in order to free some memory
        System.gc();

        AlgorithmOutput output = new AlgorithmOutput("", AlgorithmType.CGNR, outputImageLength, 
                outputImageLength*outputImageLength, Constants.dateFormatter.format(start), 
                Constants.timeFormatter.format(start), Constants.timeFormatter.format(LocalDateTime.now()),
                i, (System.currentTimeMillis() - startTime) + "ms");

        String imageFilename = output.setAndGetImageName(algorithmInput.getUserName());
        if(algorithmInput.isSaveFile()) {
            Image.generateImageOutput(f, outputImageLength, imageFilename);
        }
        return output;
    }


    public static void main(String[] args) {
        //new Thread(new LoadMonitor(1000)).start();
        FloatMatrix arrayG = CsvParser.readFloatMatrixFromCsvFile(
                Constants.MODEL_1_G_MATRIX);
        FloatMatrix matrixH = CsvParser.readFloatMatrixFromCsvFile(
                Constants.MODEL_1_H_MATRIX);
        Algorithm cgnr = new CGNR();
        AlgorithmOutput output = cgnr.run(arrayG, null);
    }
}
