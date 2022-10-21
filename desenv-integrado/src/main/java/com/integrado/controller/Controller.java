package com.integrado.controller;

import org.jblas.FloatMatrix;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.integrado.algorithm.Algorithm;
import com.integrado.algorithm.Algorithm.Model;
import com.integrado.algorithm.AlgorithmOutput;
import com.integrado.algorithm.CGNR;
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
public class Controller {
    FloatMatrix arrayG = CsvParser.readFloatMatrixFromCsvFile(
            Constants.PATH_TO_MODEL_2_MATRIXES + Constants.MODEL_1_G_MATRIX);
    FloatMatrix matrixH = CsvParser.readFloatMatrixFromCsvFile(
            Constants.PATH_TO_MODEL_2_MATRIXES + Constants.MODEL_1_H_MATRIX);

    /**
     * Get status from server.
     * 
     * @return
     */
    @GetMapping("/status") //specify which address is going to call this method
    public ResponseEntity<String> getStatus() {
        return new ResponseEntity<String>("Server is running :)", HttpStatus.OK);
    }

    @GetMapping("/startClient")
    public ResponseEntity<String> startClient() {
        return new ResponseEntity<String>("Server is running :)", HttpStatus.OK);
    }

    @PostMapping("/jblas")
    public ResponseEntity<AlgorithmOutput> getArray(@RequestBody AlgorithmInputDTO example) {
        System.out.println(example);
        Algorithm cgne = new CGNR();
        AlgorithmOutput output = cgne.run(matrixH, arrayG, Model.one);
        Image.generateImageOutput(output, example.getUser());

        return new ResponseEntity<AlgorithmOutput>(output, HttpStatus.OK);
    }
}
