package com.mannor.mealscoming.entity;

import cn.hutool.core.date.DateTime;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


@Data
@Component
public class Merchant implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String merchantName;
    private LocalDateTime createTime;
    private LocalDateTime  updateTime;
}