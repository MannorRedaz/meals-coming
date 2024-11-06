package com.mannor.reggie_take_out.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.mannor.reggie_take_out.Utils")
@ComponentScan(basePackages = "com.mannor.reggie_take_out.service")
public class AppConfig {
   // 其他配置
}
