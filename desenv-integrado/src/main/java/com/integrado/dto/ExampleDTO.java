package com.integrado.dto;

import lombok.Data;

@Data
public class ExampleDTO {

    //double[] array;
    private String array;

    @Override
    public String toString() {
        return array.toString();
    }

}
