package com.integrado.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.integrado.algorithm.Algorithm;
import com.integrado.algorithm.Algorithm.Model;
import com.integrado.algorithm.AlgorithmMatrixes;
import com.integrado.algorithm.AlgorithmOutput;
import com.integrado.algorithm.CGNE;
import com.integrado.dto.AlgorithmInputDTO;
import com.integrado.model.Image;
import com.integrado.util.LoadMonitor;

/**
 * 
 * Controller receives and sends http responses
 *
 */
@RestController
@RequestMapping
public class Controller {

    AlgorithmMatrixes al = new AlgorithmMatrixes();
    /**
     * Get status from server.
     * 
     * @return
     */
    @GetMapping("/status") //specify which address is going to call this method
    public ResponseEntity<String> getStatus() {
        return new ResponseEntity<String>("Server is running :)", HttpStatus.OK);
    }

    @PostMapping("/teste") //specify which address is going to call this method
    public ResponseEntity<String> get(int teste) {
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
    public ResponseEntity<AlgorithmOutput> getArray(@RequestBody AlgorithmInputDTO example) {
        Algorithm cgne = new CGNE();
        AlgorithmOutput output = cgne.run(AlgorithmMatrixes.arrayGone, Model.one);
        Image.generateImageOutput(output, example.getUser());

        return new ResponseEntity<AlgorithmOutput>(output, HttpStatus.OK);
    }
}
//o algoritmo tem q ser capaz de se adaptar
//trabalhar com limiares

//ver se o pc tem memoria suficiente para carregar as duas matrizes, se nao, 
//carrega dinamicamente

//tentar compactar matriz na memoria?
//tem muitos zeros na matriz, pesquisar matriz esparsa, pesquisar EJML;

//implementar rajada
