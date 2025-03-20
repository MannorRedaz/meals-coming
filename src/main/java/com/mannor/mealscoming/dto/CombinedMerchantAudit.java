package com.mannor.mealscoming.dto;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Component
public class CombinedMerchantAudit {
    // Merchant 类的字段
    private Long id;
    private Long detailId;
    private String merchantName;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    // MerchantAudit 类的字段
    private Long merchantAuditId; // 避免与上面的 id 冲突，重命名为 merchantAuditId
    private Long merchantId;
    private String auditStatus;
    private String auditComment;
    private LocalDateTime auditTime;
}    