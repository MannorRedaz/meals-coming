package com.mannor.mealscoming.entity;

import cn.hutool.core.date.DateTime;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;


@Data
@Component
public class Merchant {

    private Long id;
    private Long detailId;
    private String merchantName;
    private LocalDateTime createTime;
    private LocalDateTime  updateTime;
}