package com.integrado.algorithm;

import java.io.IOException;

import org.jblas.FloatMatrix;

import com.integrado.algorithm.Algorithm.Model;
import com.integrado.util.Constants;
import com.integrado.util.CsvParser;

public class AlgorithmMatrixes {
    //load dinamically

    //maybe do a queue, you can differentiate the models and process models that are equal
    //concurrently?

    public static FloatMatrix arrayGone = null;/*CsvParser.readFloatMatrixFromCsvFile(
            Constants.MODEL_1_G_MATRIX);*/
    public static FloatMatrix matrixHone = null; /*CsvParser.readFloatMatrixFromCsvFile(
            Constants.MODEL_1_H_MATRIX);*/

    public static final FloatMatrix arrayGtwo = null;/*CsvParser.readFloatMatrixFromCsvFile(
            Constants.MODEL_2_G_MATRIX_1);*/
    public static FloatMatrix matrixHtwo = null;/* CsvParser.readFloatMatrixFromCsvFile(
            Constants.MODEL_2_H_MATRIX);*/

    static {
        /*try {
            matrixHone = FloatMatrix.loadCSVFile(Constants.MODEL_1_H_MATRIX);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    /*public static FloatMatrix getMatrixTranspose(Model model) {
        if(model.equals(Model.one)) {
            if(matrixHfirstTranspose == null)
                matrixHfirstTranspose = matrixHone.transpose();
            return matrixHfirstTranspose;
        }
        if(matrixHsecondTranspose == null)
            matrixHsecondTranspose = matrixHtwo.transpose();
        return matrixHsecondTranspose;
    }*/

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
