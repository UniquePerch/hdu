package com.hdu.hdufpga;

import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoDataSourceProxy
public class HduAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(HduAccountApplication.class, args);
    }

}
