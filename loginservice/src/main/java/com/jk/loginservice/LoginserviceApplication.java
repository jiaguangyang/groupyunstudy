package com.jk.loginservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableEurekaClient
@MapperScan("com.jk.loginservice.dao")
@SpringBootApplication
public class LoginserviceApplication {

    public static void main(String[] args) {

        SpringApplication.run(LoginserviceApplication.class, args);
    }

}
