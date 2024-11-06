package com.mannor.reggie_take_out.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mannor.reggie_take_out.dto.DishDto;
import com.mannor.reggie_take_out.entity.Dish;

import java.math.BigDecimal;


public interface DishService extends IService<Dish> {

    void removeByIdsWithFlavor(Long[] ids);

    //新增菜品，同时对菜品的口味进行存储
    void saveWithFlavor(DishDto dishDto);

    //根据id查询菜品表和口味表
    DishDto getBtIdWithFlavor(Long id);

    void updateWithFlavor(DishDto dishDto);

}
