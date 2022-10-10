package com.integrado.algorithm;

import org.jblas.FloatMatrix;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.integrado.algorithm.Algorithm.AlgorithmType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor @JsonIgnoreProperties("outputMatrix")
public class AlgorithmOutput {
    private FloatMatrix outputMatrix;
    private AlgorithmType algorithm;
    private int imageLength;
    private int pixelsLength;
    private String date;
    private String startTime;
    private String endTime;
    private int totalIterations;
    private long timeToComplete;
    
    //cpu usage
    //memory usage
}
