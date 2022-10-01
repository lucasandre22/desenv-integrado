package com.integrado.algorithm;

import org.jblas.FloatMatrix;
import org.jblas.JavaBlas;
import org.jblas.NativeBlas;

/**
 * 
 * Conjugate Gradient Method Normal Residual (Saad2003, p. 266)
 *
 */
public class CGNR implements Algorithm {

    private final static int CONVERGENCE = 1000;

    public AlgorithmOutput run(FloatMatrix matrixH, FloatMatrix arrayG) {
        FloatMatrix matrixHTransposed = matrixH.transpose();
        FloatMatrix f = FloatMatrix.zeros(1, 30*30);
        FloatMatrix r = FloatMatrix.zeros(1, 30*30);
        FloatMatrix p = FloatMatrix.zeros(30*30, 1);
        //FloatMatrix to_be_multiplied = FloatMatrix.zeros(27904, 1);
        FloatMatrix z = FloatMatrix.zeros(30*30, 1);;


        int i = 0;
        long startTime = System.currentTimeMillis();
        for(i = 0; i < CONVERGENCE; i++) {
            FloatMatrix w = matrixH.mmul(p);
            float znorm2 = NativeBlas.snrm2(z.length, z.data, 0, 1);
            float wnorm2 = NativeBlas.snrm2(z.length, w.data, 0, 1);

            float alpha = (znorm2*znorm2)/(wnorm2*wnorm2);

            f = f.add(p.mmul(alpha));
            FloatMatrix r_next = r.sub(w.mmul(alpha));
            z = matrixHTransposed.mmul(r_next);

            if(verifyError(r, r_next)) {
                System.out.println("Error achieved!");
                break;
            }

            float z1norm2 = NativeBlas.snrm2(z.length, z.data, 0, 1);
            float beta = (z1norm2*z1norm2)/(znorm2*znorm2);

            p = z.add(p.mmul(beta));
        }
        return new AlgorithmOutput(f, i, (System.currentTimeMillis() - startTime));
    }
}
