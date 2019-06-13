package com.jk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("com.jk.mapper")
@EnableDiscoveryClient
@EnableTransactionManagement
@SpringBootApplication
public class QueryserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(QueryserviceApplication.class, args);
    }

}
