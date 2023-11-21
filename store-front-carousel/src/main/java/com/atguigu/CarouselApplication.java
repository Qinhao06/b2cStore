package com.atguigu;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@MapperScan(basePackages = "com.atguigu.mapper")
@SpringBootApplication
public class CarouselApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarouselApplication.class, args);
    }

}
