package com.atguigu.user;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.atguigu.user.mapper")
@SpringBootApplication
public class FrontUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(FrontUserApplication.class, args);
    }
}
