package com.hdu.hdufpga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class HduFpgaApplicaion {

    public static void main(String[] args) {
        SpringApplication.run(HduFpgaApplicaion.class, args);
    }

}