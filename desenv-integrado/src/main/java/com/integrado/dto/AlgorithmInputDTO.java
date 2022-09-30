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

    /*@Override
    public String toString() {
        String a = "";
        for(int i = 0; i < arrayG.length; i++) {
            a += arrayG[i] + " ";
        }
        return image + " " + algorithmS + " " + algorithmN + a;
    }*/

}
