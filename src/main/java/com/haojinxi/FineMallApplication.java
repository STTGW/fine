package com.haojinxi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@MapperScan("com.haojinxi.mapper")
@ServletComponentScan
public class FineMallApplication {
    public static void main(String[] args) {
        SpringApplication.run(FineMallApplication.class, args);
    }
}
