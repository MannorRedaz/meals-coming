package com.mannor.mealscoming.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Component
public class Complaint implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String complaintType;

    private String complaintContent;

//    @TableField("user_id")
    private Long userId;

    private String handlingStatus;

    private String handlingResult;

    private LocalDateTime handlingTime;

    private Long merchantId;
}