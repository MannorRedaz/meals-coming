package com.mannor.mealscoming;

import cn.hutool.core.lang.generator.SnowflakeGenerator;
import com.mannor.mealscoming.entity.MerchantDetails;
import com.mannor.mealscoming.service.MerchantDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@SpringBootTest


public class test {


    MerchantDetailsService merchantDetailsService;
    @Test
    public void test(){
        String dateTimeStr = "2025-03-10T00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        System.out.println(LocalDateTime.parse(dateTimeStr, formatter));

    }
    @Test
    public void test1(){
        System.out.println(new SnowflakeGenerator().next());

    }
}
