package com.mannor.mealscoming.dto;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Component
public class CombinedMerchantAndDetails {
    // Merchant 类的字段
    private Long id;
    private Long detailId;
    private String merchantName;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    // MerchantDetails 类的字段
    // 因为两个类都有 id，这里为了避免混淆，将其重命名为 detailsId
    private Long detailsId;
}    