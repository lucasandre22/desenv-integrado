package com.integrado.controller;

import java.io.IOException;

import org.jblas.FloatMatrix;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.integrado.algorithm.Algorithm;
import com.integrado.algorithm.Algorithm.AlgorithmType;
import com.integrado.algorithm.Algorithm.Model;
import com.integrado.algorithm.AlgorithmMatrixes;
import com.integrado.algorithm.AlgorithmOutput;
import com.integrado.algorithm.CGNE;
import com.integrado.algorithm.CGNR;
import com.integrado.dto.AlgorithmInputDTO;
import com.integrado.model.Image;
import com.integrado.util.Constants;
import com.integrado.util.CsvParser;
import com.integrado.util.LoadMonitor;

/**
 * 
 * Controller receives and sends http responses
 *
 */
@RestController
@RequestMapping
public class ApplicationController {

    static FloatMatrix arrayGone = null;
    static FloatMatrix arrayGtwo = null;
    /**
     * Get status from server.
     * 
     * @return
     */
    @GetMapping("/status") //specify which address is going to call this method
    public ResponseEntity<String> getStatus() {
        return new ResponseEntity<String>("Server is running :)", HttpStatus.OK);
    }

    @GetMapping("/report")
    public ResponseEntity<String> getReport() {
        return new ResponseEntity<String>("Server is running :)", HttpStatus.OK);
    }

    @GetMapping("/performance")
    public ResponseEntity<Double> startClient() {
        return new ResponseEntity<Double>(LoadMonitor.getLoadAverage(), HttpStatus.OK);
    }

    @PostMapping("/process")
    public ResponseEntity<AlgorithmOutput> getArray(@RequestBody AlgorithmInputDTO algorithmInput) throws InterruptedException, IOException {
        AlgorithmOutput output = null;
        System.out.println(algorithmInput.getArrayG()[0]);
        if(algorithmInput.getModel() == Model.one) {
            output = processModelOne(algorithmInput);
        } else {
            output = processModelTwo(algorithmInput);
        }

        output.setOutputMatrix(null);
        return new ResponseEntity<AlgorithmOutput>(output, HttpStatus.OK);
    }
    
    public static AlgorithmOutput processModelOne(AlgorithmInputDTO algorithmInput) {
        try {
            if(arrayGone == null)
                arrayGone = FloatMatrix.loadCSVFile(Constants.MODEL_1_G_MATRIX);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Algorithm algorithm = getAlgorithmInstance(algorithmInput.getType());
        AlgorithmOutput output = algorithm.run(arrayGone, algorithmInput);
        //call garbage collector in order to free some memory
        System.gc();
        return output;
    }

    public static AlgorithmOutput processModelTwo(AlgorithmInputDTO algorithmInput) {
        try {
            if(arrayGtwo == null)
                arrayGtwo = FloatMatrix.loadCSVFile(Constants.MODEL_2_G_MATRIX_1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Algorithm algorithm = getAlgorithmInstance(algorithmInput.getType());
        AlgorithmOutput output = algorithm.run(arrayGtwo, algorithmInput);
        //call garbage collector in order to free some memory
        System.gc();
        return output;
    }

    public static Algorithm getAlgorithmInstance(AlgorithmType type) {
        return type == AlgorithmType.CGNE ? new CGNE() : new CGNR();
    }
}
//o algoritmo tem q ser capaz de se adaptar
//trabalhar com limiares

//ver se o pc tem memoria suficiente para carregar as duas matrizes, se nao, 
//carrega dinamicamente

//tentar compactar matriz na memoria?
//tem muitos zeros na matriz, pesquisar matriz esparsa, pesquisar EJML;

//implementar rajada
