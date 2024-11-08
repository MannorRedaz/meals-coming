package com.mannor.mealscoming;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@ServletComponentScan
@Slf4j
@SpringBootApplication
@EnableTransactionManagement
@EnableCaching //开启Spring Cache缓存注解的功能
public class MealsComingApplication {
    public static void main(String[] args) {
        SpringApplication.run(MealsComingApplication.class, args);
        log.info("spring项目meals-coming启动成功...");
        log.info("前端项目地址：http://localhost:8080/front/page/login.html");
        log.info("后台项目地址：http://localhost:8080/backend/page/login/login.html");
//        System.out.println("spring项目启动成功...");
    }
}
