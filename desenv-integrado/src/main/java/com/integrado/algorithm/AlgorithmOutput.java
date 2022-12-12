package com.integrado.algorithm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.integrado.algorithm.Algorithm.AlgorithmType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor @JsonIgnoreProperties("outputMatrix")
public class AlgorithmOutput {
    private String image;
    private AlgorithmType algorithm;
    private int imageLength;
    private int pixelsLength;
    private String date;
    private String startTime;
    private String endTime;
    private int totalIterations;
    private String timeToComplete;

    //cpu usage
    //memory usage

    public String setAndGetImageName(String username) {
        this.image = username + "_" + this.getAlgorithm() + "_" +
                this.getDate() + "_" + this.getStartTime() + "_" +
                this.getDate() + "_" + this.getEndTime() + "_" +
                this.getPixelsLength() + "_" + this.getTotalIterations() + ".bmp";
        return image;
    }
}
