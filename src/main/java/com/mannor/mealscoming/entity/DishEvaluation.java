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

    private Long dish_id;

    private Long evaluator_id;

    private String evaluation_content;

    private LocalDateTime evaluation_time;

    private BigDecimal score;
}