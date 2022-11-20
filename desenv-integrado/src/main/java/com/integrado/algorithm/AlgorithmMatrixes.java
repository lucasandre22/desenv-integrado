package com.integrado.algorithm;

import org.jblas.FloatMatrix;

import com.integrado.algorithm.Algorithm.Model;
import com.integrado.util.Constants;
import com.integrado.util.CsvParser;

public class AlgorithmMatrixes {
    //load dinamically

    //maybe do a queue, you can differentiate the models and process models that are equal
    //concurrently?

    public static final FloatMatrix arrayGone = CsvParser.readFloatMatrixFromCsvFile(
            Constants.MODEL_1_G_MATRIX);
    private static final FloatMatrix matrixHone = CsvParser.readFloatMatrixFromCsvFile(
            Constants.MODEL_1_H_MATRIX);

    public static final FloatMatrix arrayGtwo = null;/*CsvParser.readFloatMatrixFromCsvFile(
            Constants.MODEL_2_G_MATRIX_1);*/
    private static final FloatMatrix matrixHtwo = null;/* CsvParser.readFloatMatrixFromCsvFile(
            Constants.MODEL_2_H_MATRIX);*/

    private static FloatMatrix matrixHfirstTranspose = null;
    private static FloatMatrix matrixHsecondTranspose = null;

    public static FloatMatrix getMatrixTranspose(Model model) {
        if(model.equals(Model.one)) {
            //if(matrixHfirstTranspose == null)
                //matrixHfirstTranspose = matrixHone.transpose();
            return matrixHone.transpose();
        }
        if(matrixHsecondTranspose == null)
            matrixHsecondTranspose = matrixHtwo.transpose();
        return matrixHsecondTranspose;
    }
    
    public static FloatMatrix getMatrixH(Model model) {
        //return model.equals(Model.one) ? matrixHone : matrixHtwo;
        if(model.equals(Model.one)) {
            return matrixHone;
        }
        return matrixHtwo;
    }

}
