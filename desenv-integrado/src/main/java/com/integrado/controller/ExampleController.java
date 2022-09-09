package com.integrado.controller;

import org.jblas.DoubleMatrix;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
