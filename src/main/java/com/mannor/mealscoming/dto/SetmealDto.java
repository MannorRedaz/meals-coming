package com.mannor.mealscoming.dto;


import com.mannor.mealscoming.entity.Setmeal;
import com.mannor.mealscoming.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;

    private Integer saleNum;//月销
}
