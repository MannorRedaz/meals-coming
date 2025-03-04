package com.mannor.mealscoming.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Component
public class CustomerComplaintSuggestion implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String complaint_type;

    private String complaint_content;

    private Long order_id;

    private Long user_id;

    private LocalDateTime submit_time;
}