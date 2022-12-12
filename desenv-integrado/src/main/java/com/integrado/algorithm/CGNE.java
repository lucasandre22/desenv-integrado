package com.integrado.algorithm;

import java.time.LocalDateTime;

import org.jblas.FloatMatrix;

import com.integrado.dto.AlgorithmInputDTO;
import com.integrado.model.Image;
import com.integrado.util.Constants;
import com.integrado.util.CsvParser;
import com.integrado.util.LoadMonitor;

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
    @Override
    public AlgorithmOutput run(FloatMatrix arrayG, AlgorithmInputDTO algorithmInput) throws OutOfMemoryError {
        LocalDateTime start = LocalDateTime.now();
        Model model = algorithmInput.getModel();

        //define output image length by model
        int outputImageLength = model == Model.one ? 60 : 30;
        FloatMatrix f = FloatMatrix.zeros(1, outputImageLength*outputImageLength);
        FloatMatrix r = arrayG;
        FloatMatrix p = AlgorithmMatrixes.getMatrixH(model).transpose().mmul(r);
        LoadMonitor.increaseMemoryAvailable(algorithmInput.getModel());
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
            r_next = r.sub(AlgorithmMatrixes.getMatrixH(model).mmul(alpha).mmul(p));

            if(verifyError(r, r_next)) {
                //System.out.println("Error achieved!");
                break;
            }

            new_r_dot = r_next.dot(r_next);
            beta = new_r_dot/r_dot;
            r_dot = new_r_dot;
            p = AlgorithmMatrixes.getMatrixH(model).transpose().mmul(r_next).add(p.mmul(beta));
            r = r_next;
        }
        r_next = null;
        r = null;
        p = null;
        arrayG = null;
        //call garbage collector in order to free some memory
        System.gc();

        AlgorithmOutput output = new AlgorithmOutput("", AlgorithmType.CGNE, outputImageLength, 
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
        FloatMatrix arrayG = CsvParser.readFloatMatrixFromCsvFile(Constants.MODEL_1_G_MATRIX);
        //FloatMatrix matrixH = CsvParser.readFloatMatrixFromCsvFile(Constants.MODEL_1_H_MATRIX);
        AlgorithmInputDTO teste = new AlgorithmInputDTO();
        teste.setArrayG(null);
        teste.setModel(Model.one);
        teste.setUserName("TESTEEE");
        teste.setType(AlgorithmType.CGNR);
        Algorithm cgne = new CGNE();
        AlgorithmOutput output = cgne.run(arrayG, teste);
    }
}
