package com.integrado.algorithm;

import org.jblas.DoubleMatrix;

import com.integrado.util.CsvParser;

import lombok.Data;

/**
 * 
 * Conjugate Gradient Method Normal Error
 *
 */
@Data
public class CGNE implements Algorithm {

    private static final String PATH_TO_MATRIXES = "../modelo1/";
    private DoubleMatrix arrayG = new DoubleMatrix(CsvParser.readDoubleMatrixFromCsvFile(PATH_TO_MATRIXES + "g-30x30-1.csv"));
    private DoubleMatrix matrixH = new DoubleMatrix(CsvParser.readDoubleMatrixFromCsvFile(PATH_TO_MATRIXES + "H-2.csv"));

    public static void main(String[] args) {
        CGNE teste = new CGNE();
        DoubleMatrix f = DoubleMatrix.zeros(1, 30);
        DoubleMatrix r = DoubleMatrix.zeros(1, 30);
        DoubleMatrix p;
        //tamanho f = SxN
        //tamanho dos vetores eh 30x30
        //27904 x 900, 30 x 30 pixels, S = 436, N = 64).
        // 
        f.put(0, 0, 0);
        //f0 sempre com vetor todo
        
        //r.put(0, 0, teste.arrayG.sub(teste.matrixH.mul(f.get(0, 0))));
        //p.put(0, 0, teste.matrixH.transpose().dot(r.get(0, 0)));
        //p.put(0, 0, teste.matrixH.transpose().(r.get(0, 0)));

        teste.arrayG.dot(null);

    }
}
