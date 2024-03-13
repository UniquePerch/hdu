package com.hdu.hdufpga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class HduRecordApplication {

    public static void main(String[] args) {
        SpringApplication.run(HduRecordApplication.class, args);
    }

}
