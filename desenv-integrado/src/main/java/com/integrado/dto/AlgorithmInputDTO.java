package com.integrado.dto;

import com.integrado.algorithm.Algorithm.AlgorithmType;
import com.integrado.algorithm.Algorithm.Model;

import lombok.Data;

@Data
public class AlgorithmInputDTO {
    private String user;
    private double[] arrayG;
    private AlgorithmType type;
    private Model model;

}
