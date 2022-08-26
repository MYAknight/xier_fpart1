package com.healthcare;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.healthcare.mapper")
public class TestHCApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestHCApplication.class, args);
    }
}
