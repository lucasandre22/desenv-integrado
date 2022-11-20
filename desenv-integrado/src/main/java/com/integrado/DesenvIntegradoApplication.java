package com.integrado;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.integrado.util.LoadMonitor;

@SpringBootApplication
public class DesenvIntegradoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DesenvIntegradoApplication.class, args);
        new Thread(new LoadMonitor(1000)).start();
    }

}
