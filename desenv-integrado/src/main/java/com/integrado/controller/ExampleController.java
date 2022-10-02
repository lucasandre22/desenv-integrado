package com.integrado.controller;

import org.jblas.DoubleMatrix;
import org.jblas.FloatMatrix;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.integrado.algorithm.Algorithm;
import com.integrado.algorithm.AlgorithmOutput;
import com.integrado.algorithm.CGNE;
import com.integrado.dto.AlgorithmInputDTO;
import com.integrado.model.Image;
import com.integrado.util.Constants;
import com.integrado.util.CsvParser;

/**
 * 
 * Controller receives and sends http responses
 *
 */
@RestController
@RequestMapping
public class ExampleController {
    FloatMatrix arrayG = CsvParser.readFloatMatrixFromCsvFile(
            Constants.PATH_TO_MODEL_2_MATRIXES + Constants.MODEL_2_G_MATRIX_2);
    FloatMatrix matrixH = CsvParser.readFloatMatrixFromCsvFile(
            Constants.PATH_TO_MODEL_2_MATRIXES + Constants.MODEL_2_H_MATRIX);

    /**
     * Get status from server.
     * 
     * @return
     */
    @GetMapping("/status") //specify which address is going to call this method
    public ResponseEntity<String> getStatus() {
        return new ResponseEntity<String>("Server is running :)", HttpStatus.OK);
    }

    /**
     * Test jBlas.
     * 
     * @return a matrix which was a result from a multiplication of matrixes.
     */
    @GetMapping("/jblas") //specify which address is going to call this method
    public String testJblas() {
        DoubleMatrix matrixA = DoubleMatrix.rand(10, 10);
        DoubleMatrix matrixB = DoubleMatrix.rand(10, 10);
        //matrix-matrix multiplication 
        return matrixA.mmul(matrixB).toString();
    }

    @PostMapping("/test") 
    public ResponseEntity<String> test(@RequestBody String example) {
        System.out.println(example);
        return new ResponseEntity<String>(example, HttpStatus.ACCEPTED);
    }

    @PostMapping("/jblas")
    public ResponseEntity<AlgorithmOutput> getArray(@RequestBody AlgorithmInputDTO example) {
        System.out.println(example);
        Algorithm cgne = new CGNE();
        AlgorithmOutput output = cgne.run(matrixH, arrayG);
        Image.saveFloatMatrixToImage(output.getOutputMatrix(), 30, 30, example.getUser());
        return new ResponseEntity<AlgorithmOutput>(output, HttpStatus.OK);
    }
}
