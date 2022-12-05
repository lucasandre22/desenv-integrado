package com.integrado.controller;

import java.io.IOException;
import java.util.PriorityQueue;

import org.jblas.FloatMatrix;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.integrado.algorithm.Algorithm;
import com.integrado.algorithm.Algorithm.AlgorithmType;
import com.integrado.algorithm.AlgorithmOutput;
import com.integrado.algorithm.CGNE;
import com.integrado.algorithm.CGNR;
import com.integrado.dto.AlgorithmInputDTO;
import com.integrado.util.LoadMonitor;
import com.integrado.util.Report;

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
    private PriorityQueue<Task> queue;
    /**
     * Get status from server.
     * 
     * @return
     */
    @GetMapping("/status") //specify which address is going to call this method
    public ResponseEntity<String> getStatus() {
        return new ResponseEntity<String>("Server is running :)", HttpStatus.OK);
    }

    @GetMapping("/reports")
    public ResponseEntity<Report> getReport() {
        return new ResponseEntity<Report>(new Report(LoadMonitor.freeMemory, LoadMonitor.usedMemory, LoadMonitor.getLoadAverage()), HttpStatus.OK);
    }

    @PostMapping("/process")
    public ResponseEntity<AlgorithmOutput> process(@RequestBody AlgorithmInputDTO algorithmInput) throws InterruptedException, IOException {
        AlgorithmOutput output = runAlgorithm(algorithmInput);
        return new ResponseEntity<AlgorithmOutput>(output, HttpStatus.OK);
    }
    
    public static AlgorithmOutput runAlgorithm(AlgorithmInputDTO algorithmInput) {
        Algorithm algorithm = getAlgorithmInstance(algorithmInput.getType());
        FloatMatrix arrayG = new FloatMatrix(algorithmInput.getArrayG());
        System.out.println("LENGTH: " + arrayG.length);
        System.out.println("LENGTH array: " + algorithmInput.getArrayG().length);
        AlgorithmOutput output = algorithm.run(arrayG, algorithmInput);

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
