package com.integrado.algorithm;

import org.jblas.FloatMatrix;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class AlgorithmOutput {
    private FloatMatrix outputMatrix;
    private int totalIterations;
    private long timeToComplete;
}
