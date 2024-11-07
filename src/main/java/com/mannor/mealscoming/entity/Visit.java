package com.mannor.mealscoming.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Visit implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    //访问时间
    private Date loginTime;

    //IP地址
    private InetAddress ipAddress;
}
