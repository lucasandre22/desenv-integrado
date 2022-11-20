package com.integrado.util;

import java.time.format.DateTimeFormatter;

/**
 * 
 * Mathematical constants based on each model provided.
 * 
 *
 */
public class Constants {
    private static final String INPUT_FILE_TYPE = "csv";

    public final static int CONVERGENCE = 20;

    public static final String PATH_TO_MODEL_1_MATRIXES = "../model1/";
    public static final int MODEL_1_S = 794;
    public static final int MODEL_1_N = 64;
    public static final String MODEL_1_G_MATRIX = PATH_TO_MODEL_1_MATRIXES + "G-1." + INPUT_FILE_TYPE;
    public static final String MODEL_1_H_MATRIX = PATH_TO_MODEL_1_MATRIXES + "H-1." + INPUT_FILE_TYPE;
    
    public static final String PATH_TO_MODEL_2_MATRIXES = "../model2/";
    public static final int MODEL_2_S = 436;
    public static final int MODEL_2_N = 64;
    public static final String MODEL_2_G_MATRIX_1 = PATH_TO_MODEL_2_MATRIXES + "g-30x30-1." + INPUT_FILE_TYPE;
    public static final String MODEL_2_G_MATRIX_2 = PATH_TO_MODEL_2_MATRIXES + "g-30x30-2." + INPUT_FILE_TYPE;
    public static final String MODEL_2_H_MATRIX = PATH_TO_MODEL_2_MATRIXES + "H-2." + INPUT_FILE_TYPE;

    public static final float ERROR = 0.0001f;

    public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss"); 
}
