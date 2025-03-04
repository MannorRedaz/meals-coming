package com.mannor.mealscoming.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Component
public class EvaluationManagement implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String evaluationContent;

    private Long userId;

    private Long evaluatedObjectId;

    private String evaluatedObjectType;

    private LocalDateTime evaluationTime;

    private Short score;
}