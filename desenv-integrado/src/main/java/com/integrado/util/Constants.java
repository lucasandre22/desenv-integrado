package com.integrado.util;

/**
 * 
 * Mathematical constants based on each model provided.
 * 
 *
 */
public class Constants {
    private static final String FILE_TYPE = "csv";

    public final static int CONVERGENCE = 100;

    public static final String PATH_TO_MODEL_1_MATRIXES = "../model1/";
    public static final int MODEL_1_S = 794;
    public static final int MODEL_1_N = 64;
    public static final String MODEL_1_G_MATRIX = "G-1." + FILE_TYPE;
    public static final String MODEL_1_H_MATRIX = "H-1." + FILE_TYPE;
    
    public static final String PATH_TO_MODEL_2_MATRIXES = "../model2/";
    public static final int MODEL_2_S = 436;
    public static final int MODEL_2_N = 64;
    public static final String MODEL_2_G_MATRIX = "g-30x30-1." + FILE_TYPE;
    public static final String MODEL_2_H_MATRIX = "H-2." + FILE_TYPE;

    public static final float ERROR = 0.0001f;

}
