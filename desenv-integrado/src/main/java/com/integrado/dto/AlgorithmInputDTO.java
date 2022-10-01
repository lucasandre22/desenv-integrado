package com.integrado.dto;

import lombok.Data;

@Data
public class AlgorithmInputDTO {

    private String user;
    private boolean signalGain;
    private double[] arrayG;
    private String image;
    private int algorithmS;
    private int algorithmN;

}
