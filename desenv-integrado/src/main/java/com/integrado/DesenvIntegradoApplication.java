package com.integrado;


import java.io.IOException;

import org.jblas.FloatMatrix;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.integrado.algorithm.AlgorithmMatrixes;
import com.integrado.util.Constants;
import com.integrado.util.LoadMonitor;

@SpringBootApplication
public class DesenvIntegradoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DesenvIntegradoApplication.class, args);
        try {
            AlgorithmMatrixes.matrixHone = FloatMatrix.loadCSVFile(Constants.MODEL_1_H_MATRIX);
            AlgorithmMatrixes.matrixHtwo = FloatMatrix.loadCSVFile(Constants.MODEL_2_H_MATRIX);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(new LoadMonitor(100)).start();
        System.gc();
    }

    public static void seeAvailableMemory() {
        //System.out.println("Available memory: " + );
    }
}
