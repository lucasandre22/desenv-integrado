package com.integrado.algorithm;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.jblas.FloatMatrix;

import com.integrado.algorithm.Algorithm.AlgorithmType;
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
    public AlgorithmOutput run(FloatMatrix arrayG, AlgorithmInputDTO algorithmInput) {
        LocalDateTime start = LocalDateTime.now();
        Model model = algorithmInput.getModel();

        //define output image length by model
        int outputImageLength = model == Model.one ? 60 : 30;
        FloatMatrix f = FloatMatrix.zeros(1, outputImageLength*outputImageLength);
        FloatMatrix r = arrayG;
        FloatMatrix p = AlgorithmMatrixes.getMatrixH(model).transpose().mmul(r);
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
                System.out.println("Error achieved!");
                break;
            }

            new_r_dot = r_next.dot(r_next);
            beta = new_r_dot/r_dot;
            r_dot = new_r_dot;
            p = AlgorithmMatrixes.getMatrixH(model).transpose().mmul(r_next).add(p.mmul(beta));
            r = r_next;
        }

        AlgorithmOutput output = new AlgorithmOutput("", AlgorithmType.CGNR, outputImageLength, 
                outputImageLength*outputImageLength, Constants.dateFormatter.format(start), 
                Constants.timeFormatter.format(start), Constants.timeFormatter.format(LocalDateTime.now()),
                i, (System.currentTimeMillis() - startTime));

        String imageFilename = output.setAndGetImageName(algorithmInput.getUserName());
        Image.generateImageOutput(f, outputImageLength, imageFilename);
        return output;
    }

    public static void main(String[] args) {
        new Thread(new LoadMonitor(1000)).start();
        FloatMatrix arrayG = CsvParser.readFloatMatrixFromCsvFile(Constants.MODEL_1_G_MATRIX);
        //FloatMatrix matrixH = CsvParser.readFloatMatrixFromCsvFile(Constants.MODEL_1_H_MATRIX);
        AlgorithmInputDTO teste = new AlgorithmInputDTO();
        teste.setArrayG(null);
        teste.setModel(Model.one);
        teste.setUser("TESTEEE");
        teste.setType(AlgorithmType.CGNR);
        Algorithm cgne = new CGNE();
        AlgorithmOutput output = cgne.run(arrayG, teste);
    }
}
