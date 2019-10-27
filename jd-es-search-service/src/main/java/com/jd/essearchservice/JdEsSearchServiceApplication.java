package com.jd.essearchservice;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan(basePackages = "com.jd.essearchservice.mapper")
@EnableDubbo
@SpringBootApplication
public class JdEsSearchServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(JdEsSearchServiceApplication.class, args);
    }

}
