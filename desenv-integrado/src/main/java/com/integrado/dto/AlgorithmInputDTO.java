package com.integrado.dto;

import com.integrado.algorithm.Algorithm.AlgorithmType;
import com.integrado.algorithm.Algorithm.Model;
import com.integrado.util.LoadMonitor;
import com.integrado.algorithm.AlgorithmOutput;

import lombok.Data;

@Data
public class AlgorithmInputDTO {
    private String userName;
    private float[] arrayG;
    private AlgorithmType type;
    private Model model;
    private boolean saveFile = true;

    /*public void setModel(Model model) {
        if(model == Model.one)
            LoadMonitor.isAboutToProcessModelOne.set(true);
        else
            LoadMonitor.isAboutToProcessModelTwo.set(false);
        this.model = model;
    }*/
}
