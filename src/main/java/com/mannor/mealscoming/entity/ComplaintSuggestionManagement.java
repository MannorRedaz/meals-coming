package com.mannor.mealscoming.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Component
public class ComplaintSuggestionManagement implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String complaint_type;

    private String complaint_content;

    private Long user_id;

    private String handling_status;

    private String handling_result;

    private LocalDateTime handling_time;
}