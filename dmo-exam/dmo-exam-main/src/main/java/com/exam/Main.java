package com.exam;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan(basePackages = { "com.exam" })
@MapperScan("com.exam.dao")
public class Main  {

    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);
    }
}
