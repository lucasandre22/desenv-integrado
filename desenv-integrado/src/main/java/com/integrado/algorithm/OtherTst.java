package com.integrado.algorithm;

import java.io.FileReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.ejml.UtilEjml;
import org.ejml.concurrency.EjmlConcurrency;
import org.ejml.data.DMatrixRMaj;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.dense.row.CommonOps_MT_DDRM;
import org.ejml.dense.row.RandomMatrices_DDRM;

import com.opencsv.CSVReader;

public class OtherTst {
    public static void main( String[] args ) {
        // Create a few random matrices that we will multiply and decompose
        Random rand = new Random(0xBEEF);
        DMatrixRMaj A = RandomMatrices_DDRM.rectangle(4000, 4000, -1, 1, rand);
        DMatrixRMaj B = RandomMatrices_DDRM.rectangle(A.numCols, 1000, -1, 1, rand);
        DMatrixRMaj C = new DMatrixRMaj(1, 1);

        // First do a concurrent matrix multiply using the default number of threads
        System.out.println("Matrix Multiply, threads=" + EjmlConcurrency.getMaxThreads());
        UtilEjml.printTime("  ", "Elapsed: ", () -> CommonOps_MT_DDRM.mult(A, B, C));

        // Set it to two threads
        EjmlConcurrency.setMaxThreads(2);
        System.out.println("Matrix Multiply, threads=" + EjmlConcurrency.getMaxThreads());
        UtilEjml.printTime("  ", "Elapsed: ", () -> CommonOps_MT_DDRM.mult(A, B, C));

        // Then let's compare it against the single thread implementation
        System.out.println("Matrix Multiply, Single Thread");
        UtilEjml.printTime("  ", "Elapsed: ", () -> CommonOps_DDRM.mult(A, B, C));

        // Setting the number of threads to 1 then running am MT implementation actually calls completely different
        // code than the regular function calls and will be less efficient. This will probably only be evident on
        // small matrices though

        // If the future we will provide a way to optionally automatically switch to concurrent implementations
        // for larger when calling standard functions.
    }
}
