package com.cxk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 */
@SpringBootApplication
@MapperScan("com.cxk.mapper")
public class KeepAccountMain {
    public static void main(String[] args) {
        SpringApplication.run(KeepAccountMain.class, args);
    }

}
