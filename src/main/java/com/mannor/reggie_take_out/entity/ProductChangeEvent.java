package com.mannor.reggie_take_out.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductChangeEvent {
    private long productId;
    private Integer type; //1:dish  2:setmeal

}
