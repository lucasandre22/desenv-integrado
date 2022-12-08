package com.integrado.algorithm;

import org.jblas.FloatMatrix;

import com.integrado.algorithm.Algorithm.Model;

public class AlgorithmMatrixes {

    public static FloatMatrix arrayGone = null;
    public static FloatMatrix matrixHone = null;

    public static final FloatMatrix arrayGtwo = null;
    public static FloatMatrix matrixHtwo = null;

    static {
        /*try {
            matrixHone = FloatMatrix.loadCSVFile(Constants.MODEL_1_H_MATRIX);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    public static FloatMatrix getMatrixTranspose(Model model) {
        if(model.equals(Model.one)) {
            return matrixHone.transpose();
        }
        return matrixHtwo.transpose();
    }

    public static FloatMatrix getMatrixH(Model model) {
        if(model.equals(Model.one)) {
            return matrixHone;
        }
        return matrixHtwo;
    }

}
