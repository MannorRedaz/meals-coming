package com.mannor.mealscoming.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.mannor.mealscoming.Utils")
@ComponentScan(basePackages = "com.mannor.mealscoming.service")
public class AppConfig {
   // 其他配置
}
