package com.integrado.algorithm;

import org.jblas.FloatMatrix;

import com.integrado.util.Constants;

public interface Algorithm {

    public AlgorithmOutput run(FloatMatrix matrixH, FloatMatrix arrayG);
    
    public default boolean verifyError(FloatMatrix r, FloatMatrix r_next) {
        System.out.println("Current error: " + Math.abs(r_next.norm2() - r.norm2()));
        return Math.abs(r_next.norm2() - r.norm2()) < Constants.ERROR;
    }
}
