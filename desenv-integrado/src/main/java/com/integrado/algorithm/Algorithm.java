package com.integrado.algorithm;

import org.jblas.FloatMatrix;

import com.integrado.dto.AlgorithmInputDTO;
import com.integrado.util.Constants;

public interface Algorithm { 

    public AlgorithmOutput run(FloatMatrix arrayG, AlgorithmInputDTO algorithmInput) throws OutOfMemoryError;

    public default boolean verifyError(FloatMatrix r, FloatMatrix r_next) {
        //System.out.println("Current error: " + Math.abs(r_next.norm2() - r.norm2()));
        return Math.abs(r_next.norm2() - r.norm2()) < Constants.ERROR;
    }

    /**
     * Algorithm input model
     *
     */
    public enum Model {
        one,
        two
    }

    public enum AlgorithmType {
        CGNE,
        CGNR
    }

}
