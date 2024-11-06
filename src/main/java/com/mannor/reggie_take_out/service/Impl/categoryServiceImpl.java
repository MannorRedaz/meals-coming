package com.mannor.reggie_take_out.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mannor.reggie_take_out.common.CustomException;
import com.mannor.reggie_take_out.entity.Category;
import com.mannor.reggie_take_out.entity.Dish;
import com.mannor.reggie_take_out.entity.Setmeal;
import com.mannor.reggie_take_out.mapper.CategoryMapper;
import com.mannor.reggie_take_out.service.CategoryService;
import com.mannor.reggie_take_out.service.DishService;
import com.mannor.reggie_take_out.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class categoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    /**
     * 根据id删除分类，删除之前判断是否有关联的数据
     *
     * @param id
     */
    @Override
    public void remove(Long id) {

        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);//添加查询条件--》id
        int count1 = dishService.count(dishLambdaQueryWrapper);
        //查询当前分类是否关联了菜品，如果已经关联，抛出一个业务异常
        if (count1 > 0) {
            //关联了菜品，抛出业务异常
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }


        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);//添加查询条件--》id
        int count2 = setmealService.count(setmealLambdaQueryWrapper);
        //查询当前分类是否关联了套餐，如果已经关联，抛出一个业务异常
        if (count1 > 0) {
            //关联了套餐，抛出业务异常
            throw new CustomException("当前分类下关联了套餐，不能删除");
        }


        //正常删除分类
        super.removeById(id);

    }
}
