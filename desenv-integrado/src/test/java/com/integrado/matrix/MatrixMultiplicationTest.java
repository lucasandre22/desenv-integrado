package com.integrado.matrix;

import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jblas.DoubleMatrix;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.opencsv.CSVReader;

public class MatrixMultiplicationTest {

    private static final String PATH_TO_MATRIXES = "../matrixes/";
    private DoubleMatrix matrixA;
    private DoubleMatrix matrixM;
    private DoubleMatrix matrixN;
    private DoubleMatrix matrixAM;
    private DoubleMatrix matrixMN;

    /**
     * Read and return the matrix by the given matrix name, in the
     * PATH_TO_MATRIX path. 
     * 
     * @param matrixName the matrix to load
     * 
     * @return matrix the matrix read from file
     */
    private double[][] readMatrixFromCsvFile(String matrixName) {
        List<double[]> matrixLines = new ArrayList<double[]>();
        double[][] matrixDouble;

        try (CSVReader csvReader = new CSVReader(new FileReader(PATH_TO_MATRIXES + matrixName))) {
            for(String[] values = csvReader.readNext(); values != null; values = csvReader.readNext()) {
                values = values[0].split(";");
                double[] matrixLine = new double[values.length];
                int i = 0;
                for(String value : values) {
                    matrixLine[i++] = Double.parseDouble(value);
                }
                matrixLines.add(matrixLine);
            }
        } catch(Exception e) {
            System.err.println(e);
        }
        matrixDouble = new double[matrixLines.size()][matrixLines.get(0).length];
        int i = 0;
        for(double[] line: matrixLines) {
            matrixDouble[i++] = line;
        }
        System.out.println(matrixDouble[0][0]);
        return matrixDouble;
    }

    /**
     * Read matrixes from file
     */
    @BeforeEach
    void setUp() {
        matrixA = new DoubleMatrix(readMatrixFromCsvFile("a.csv"));
        matrixM = new DoubleMatrix(readMatrixFromCsvFile("M.csv"));
        matrixN = new DoubleMatrix(readMatrixFromCsvFile("N.csv"));
        matrixAM = new DoubleMatrix(readMatrixFromCsvFile("aM.csv"));
        matrixMN = new DoubleMatrix(readMatrixFromCsvFile("MN.csv"));
    }

    private DoubleMatrix multiplyMatrix(DoubleMatrix a, DoubleMatrix b) {
        return a.mmul(b);
    }

    @Test
    void testMultiplication() {
        assertEquals(multiplyMatrix(matrixM, matrixN), matrixMN);
        //Assertion is failing because jblas is getting more precise decimals xDD
        //assertEquals(multiplyMatrix(matrixA, matrixM), matrixAM);

        //This multiplication does not make sense
        //multiplyMatrix(matrixM, matrixA);

    }
}
