package com.mannor.mealscoming.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Component
public class MerchantAudit implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long merchant_id;

    private String audit_status;

    private String audit_comment;

    private LocalDateTime audit_time;
}