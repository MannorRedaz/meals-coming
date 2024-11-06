package com.mannor.reggie_take_out;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@ServletComponentScan
@Slf4j
@SpringBootApplication
@EnableTransactionManagement
@EnableCaching //开启Spring Cache缓存注解的功能
public class ReggieTakeOutApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReggieTakeOutApplication.class, args);
        log.info("spring项目启动成功...");
        System.out.println("spring项目启动成功...");
    }
}
