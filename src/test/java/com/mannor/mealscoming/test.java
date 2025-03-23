package com.mannor.mealscoming;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class test {



    @Test
    public void test(){
        String dateTimeStr = "2025-03-10T00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        System.out.println(LocalDateTime.parse(dateTimeStr, formatter));

    }
}
