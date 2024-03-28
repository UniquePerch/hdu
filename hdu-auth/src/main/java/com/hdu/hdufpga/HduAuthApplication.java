package com.hdu.hdufpga;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class HduAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(HduAuthApplication.class, args);
    }

}