package com.mannor.mealscoming.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
@Component
public class DishEvaluation implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long dishId;

    private Long evaluatorId;

    private String evaluationContent;

    private LocalDateTime evaluationTime;

    private BigDecimal score;
}