/**
 * 评价实体类
 * 用于表示用户对某个对象的评价信息
 * 实现了Serializable接口以支持序列化，以便于在网络中传输或存储
 */
package com.mannor.mealscoming.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Component
public class Evaluation implements Serializable {

    // 序列化ID，用于标识类的版本
    private static final long serialVersionUID = 1L;

    // 评价ID，唯一标识一条评价记录
    private Long id;

    // 评价内容，存储用户具体的评价文本
    private String evaluationContent;

    // 用户ID，标识进行评价的用户
    private Long userId;

    // 被评价对象ID，标识被评价的对象
    private Long evaluatedObjectId;

    // 被评价对象类型，描述被评价对象的类型
    private String evaluatedObjectType;

    // 评价时间，记录用户进行评价的时间
    private LocalDateTime evaluationTime;

    // 评分，用户对被评价对象的评分
    private Short score;

    private Long MerchantId;
}
