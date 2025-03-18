package com.mannor.mealscoming.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Component
public class OrderComplaint implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String complaintType;

    private String complaintContent;

    private Long orderId;

    private Long userId;

    private LocalDateTime submitTime;
}