package com.hdu.hdufpga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
//TODO : Redis键过期监听器，用来释放板卡
public class HduFpgaApplicaion {

    public static void main(String[] args) {
        SpringApplication.run(HduFpgaApplicaion.class, args);
    }

}