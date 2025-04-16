package com.mannor.mealscoming;

import cn.hutool.core.lang.generator.SnowflakeGenerator;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mannor.mealscoming.common.R;
import com.mannor.mealscoming.entity.MerchantDetails;
import com.mannor.mealscoming.entity.User;
import com.mannor.mealscoming.service.MerchantDetailsService;
import com.mannor.mealscoming.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest


public class test {


    @Autowired
    private UserService userService;


    @Test
    public void test() {
        String dateTimeStr = "2025-03-10T00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        System.out.println(LocalDateTime.parse(dateTimeStr, formatter));

    }

    @Test
    public void test1() {
        System.out.println("wejian.xlsx".endsWith(".xlsx"));

    }


    @Test
    public void test2() {
        System.out.println(new SnowflakeGenerator().next());
        System.out.println(String.valueOf(new SnowflakeGenerator().next()).length());

    }
}
