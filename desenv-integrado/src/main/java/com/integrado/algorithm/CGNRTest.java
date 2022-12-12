package com.integrado.algorithm;

import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.ejml.data.DMatrixSparseCSC;
import org.ejml.data.FMatrixRMaj;
import org.ejml.data.FMatrixSparseCSC;
import org.ejml.equation.Equation;
import org.ejml.ops.DConvertMatrixStruct;
import org.ejml.ops.MatrixIO;

import com.integrado.util.Constants;
import com.opencsv.CSVReader;

public class CGNRTest {

    public static int ROWS = 100000;
    public static int COLS = 1000;
    public static int XCOLS = 1;

    public static void main(String args[]) throws IOException, InterruptedException {
        Random rand = new Random(234);
        //DMatrixSparseCSC x = RandomMatrices_DSCC.rectangle(COLS, XCOLS, (int)(XCOLS*COLS*0.7), rand);
        //FMatrixSparseCSC g = readFloatMatrixFromCsvFile(Constants.MODEL_1_H_MATRIX);
        //FMatrixSparseCSC h = readFloatMatrixFromCsvFile(Constants.MODEL_2_H_MATRIX);
        //MatrixRMaj A = MatrixIO.loadCSV(Constants.MODEL_1_H_MATRIX, 50816, 3600);
        DMatrixSparseCSC Z = DConvertMatrixStruct.convert(work, (DMatrixSparseCSC)null);
        //DMatrixSparseCSC A = RandomMatrices_DSCC.rectangle(50816, 3600, (int)(ROWS*COLS*0.07), rand);
        System.out.println("carregou");

        Thread.sleep(100000);
        A.set(8, 1, 4);
    }

    public void updateP( FMatrixSparseCSC P , FMatrixSparseCSC F , FMatrixSparseCSC Q ) {
        Equation eq = new Equation();
        eq.alias(P,"P",F,"F",Q,"Q");
        eq.process("S = F*P*F'");
        eq.process("P = S + Q");
    }
    
    public static FMatrixSparseCSC readFloatMatrixFromCsvFile(String matrixName) {
        List<float[]> matrixLines = new ArrayList<float[]>();
        //FMatrixSparseCSC output = null;
        float[] test = null;
        long start = System.currentTimeMillis();
        NumberFormat formatter = new DecimalFormat("#0.00000");

        try (CSVReader csvReader = new CSVReader(new FileReader(matrixName))) {
            for(String[] values = csvReader.readNext(); values != null; values = csvReader.readNext()) {
                float[] matrixLine = new float[values.length];
                int i = 0;
                for(String value : values) {
                    matrixLine[i++] = Float.parseFloat(value);
                }
                matrixLines.add(matrixLine);
            }
        } catch(Exception e) {
            System.err.println(e);
        }
        //test = new float[matrixLines.size()*matrixLines.get(0).length];
        FMatrixSparseCSC a = new FMatrixSparseCSC(matrixLines.size(), matrixLines.get(0).length);
        int ax = 0;
        System.out.print("Total time:");
        for(float[] line: matrixLines) {
            System.out.println(ax);
            for(int i = 0; i < line.length; i++) {
                //a.unsafe_set(ax, i, line[i]);
                //test[i] = line[i];
            }
            ax++;
        }

        System.out.print("Total time: " + formatter.format((System.currentTimeMillis() - start) / 1000d) + " seconds");
        return a;
        //return FConvertMatrixStruct.convert(FMatrixRMaj.wrap(matrixLines.size(), matrixLines.get(0).length, test), (FMatrixSparseCSC)null);
    }
}
//http://ejml.org/wiki/index.php?title=Matlab_to_EJML
//http://ejml.org/wiki/index.php?title=Example_Sparse_Matrices
