package com.mannor.mealscoming.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mannor.mealscoming.dto.SetmealDto;
import com.mannor.mealscoming.entity.Setmeal;

public interface SetmealService extends IService<Setmeal> {

    void saveWithDish(SetmealDto setmealDto);

    SetmealDto getByIdWithDish(Long id);

    void removeByIdsWithDish(Long ids[]);



    void updateWithSetmealDish(SetmealDto setmealDto);
}

