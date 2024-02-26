package com.hdu.hdufpga;

import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableFeignClients
@EnableAutoDataSourceProxy
public class HduInterruptApplication {

    public static void main(String[] args) {
        SpringApplication.run(HduInterruptApplication.class, args);
    }

}
