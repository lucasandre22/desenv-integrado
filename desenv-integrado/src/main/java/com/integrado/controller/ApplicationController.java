package com.integrado.controller;

import java.io.IOException;
import java.util.PriorityQueue;

import org.jblas.FloatMatrix;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.integrado.algorithm.Algorithm;
import com.integrado.algorithm.Algorithm.AlgorithmType;
import com.integrado.algorithm.Algorithm.Model;
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
    //private PriorityQueue<Task> queue;
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
        LoadMonitor.run();
        return new ResponseEntity<Report>(new Report(LoadMonitor.freeMemory, LoadMonitor.usedMemory, LoadMonitor.getLoadAverage()), HttpStatus.OK);
    }

    @PostMapping("/process")
    public ResponseEntity<AlgorithmOutput> process(@RequestBody AlgorithmInputDTO algorithmInput) throws InterruptedException, IOException {
        if(!LoadMonitor.hasEnoughMemory(algorithmInput.getModel())) {
            waitForMemory(algorithmInput.getModel());
        }
        LoadMonitor.lowerMemoryAvailable(algorithmInput.getModel());
        System.out.println("Processing " + algorithmInput.getModel());
        AlgorithmOutput output = runAlgorithm(algorithmInput);
        return new ResponseEntity<AlgorithmOutput>(output, HttpStatus.OK);
    }
    
    public static AlgorithmOutput runAlgorithm(AlgorithmInputDTO algorithmInput) {
        System.out.println(algorithmInput.getType());
        Algorithm algorithm = getAlgorithmInstance(algorithmInput.getType());
        FloatMatrix arrayG = new FloatMatrix(algorithmInput.getArrayG());
        AlgorithmOutput output = null;
        //if heap exception occurs inside the algorithm
        try {
            output = algorithm.run(arrayG, algorithmInput);
        } catch(Exception e) {
            LoadMonitor.increaseMemoryAvailable(algorithmInput.getModel());
            System.gc();
            throw e;
        }

        return output;
    }

    public static Algorithm getAlgorithmInstance(AlgorithmType type) {
        return type == AlgorithmType.CGNE ? new CGNE() : new CGNR();
    }

    public static void waitForMemory(Model model) {
        int i = 1;
        //implement priority queue
        while(!LoadMonitor.hasEnoughMemory(model)) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Waiting for memory... " + i++);
        }
    }
}
