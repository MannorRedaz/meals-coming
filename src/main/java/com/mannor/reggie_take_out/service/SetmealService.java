package com.mannor.reggie_take_out.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mannor.reggie_take_out.dto.SetmealDto;
import com.mannor.reggie_take_out.entity.Setmeal;

import java.math.BigDecimal;
import java.util.List;

public interface SetmealService extends IService<Setmeal> {

    void saveWithDish(SetmealDto setmealDto);

    SetmealDto getByIdWithDish(Long id);

    void removeByIdsWithDish(Long ids[]);



    void updateWithSetmealDish(SetmealDto setmealDto);
}

