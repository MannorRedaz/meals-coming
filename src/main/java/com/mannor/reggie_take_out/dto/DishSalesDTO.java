package com.mannor.reggie_take_out.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DishSalesDTO {
    //以键值对的形式设置销售的菜品和数量
    private Map<String,Integer> dish;

    //以键值对的形式设置销售的菜品类别和数量
    private Map<String,Integer> category;

    //近七天数据
    private Map<Integer,Integer> sevenSalesNum;
}
