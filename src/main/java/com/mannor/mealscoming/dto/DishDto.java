package com.mannor.mealscoming.dto;

import com.mannor.mealscoming.entity.Dish;
import com.mannor.mealscoming.entity.DishFlavor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class DishDto extends Dish {

    private Integer saleNum;//月销

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;



}
