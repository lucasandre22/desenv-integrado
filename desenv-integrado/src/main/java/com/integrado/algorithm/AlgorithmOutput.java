package com.integrado.algorithm;

import org.jblas.FloatMatrix;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor @JsonIgnoreProperties("outputMatrix")
public class AlgorithmOutput {
    private FloatMatrix outputMatrix;
    private int imageLength;
    private int totalIterations;
    private long timeToComplete;
    //cpu usage
    //memory usage
}
