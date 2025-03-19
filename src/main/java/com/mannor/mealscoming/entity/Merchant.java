package com.mannor.mealscoming.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
public class Merchant {

    private Long id;
    private Long detailId;
    private String merchantName;
    private Date createTime;
    private Date updateTime;
}