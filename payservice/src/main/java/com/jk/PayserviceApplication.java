package com.jk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@MapperScan("com.jk.mapper")
@EnableDiscoveryClient
@SpringBootApplication
public class PayserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayserviceApplication.class, args);
    }

}
