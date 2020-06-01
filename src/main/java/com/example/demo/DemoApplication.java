package com.example.demo;

import com.example.demo.batch.BatchConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        // BatchConfiguration.date=args[0];
         //BatchConfiguration.date2=args[1];

        SpringApplication.run(DemoApplication.class, args);
    }

}
