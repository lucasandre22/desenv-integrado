package com.integrado.controller;

import org.jblas.DoubleMatrix;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.integrado.dto.ExampleDTO;

/**
 * 
 * Controller receives and sends http responses
 *
 */
@RestController
@RequestMapping
public class ExampleController {

    /**
     * Get status from server.
     * 
     * @return
     */
    @GetMapping("/status") //specify which address is going to call this method
    public String getStatus() {
        return "Server is running :)";
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

    @PostMapping("/testa") 
    public ResponseEntity<String> atos(@RequestBody String example) {
        System.out.println(example);
        return new ResponseEntity<String>(example, HttpStatus.ACCEPTED);
    }

    @PostMapping("/jblas")
    public ResponseEntity<String> getArray(@RequestBody ExampleDTO example) {
        return new ResponseEntity<String>(example.toString(), HttpStatus.ACCEPTED);
    }
}
