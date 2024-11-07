package com.mannor.mealscoming.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mannor.mealscoming.dto.DishSalesDTO;
import com.mannor.mealscoming.entity.*;
import com.mannor.mealscoming.mapper.OrderDetailMapper;
import com.mannor.mealscoming.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.w3c.dom.ls.LSOutput;
//import sun.security.pkcs11.wrapper.Functions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

    @Autowired
    private OrdersService ordersService;


    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    /**
     * 查询所有的菜品种类，对应菜品种类的售出份数
     *
     * @return
     */
    @Override
    public DishSalesDTO getDishSales() {
        List<OrderDetail> orderDetails = this.getBaseMapper().selectList(new LambdaQueryWrapper<OrderDetail>());
        Map<String, Integer> dishMsg = new HashMap<>();
        orderDetails.forEach(item -> dishMsg.merge(item.getName(), item.getNumber(), Integer::sum));

        List<Category> categories = categoryService.getBaseMapper().selectList(new LambdaQueryWrapper<Category>());
        Map<String, Integer> categoryMsg = new HashMap<>();
        categories.forEach(item -> categoryMsg.put(item.getName(), 0));

        dishMsg.forEach((dishName, sales) -> {
            try {
                Dish dish = dishService.getOne(new LambdaQueryWrapper<Dish>().eq(Dish::getName, dishName));
                String categoryName = categoryService.getById(dish.getCategoryId()).getName();
                categoryMsg.merge(categoryName, sales, Integer::sum);
            } catch (NullPointerException e) {
                Setmeal setmeal = setmealService.getOne(new LambdaQueryWrapper<Setmeal>().eq(Setmeal::getName, dishName));
                String categoryName = categoryService.getById(setmeal.getCategoryId()).getName();
                categoryMsg.merge(categoryName, sales, Integer::sum);
            }
        });
        // System.out.println(categoryMsg);

        //查询近七天的数据
        LocalDateTime now = LocalDateTime.now();
        // 计算七天之前的日期时间
        HashMap<Integer, Integer> sevenSalesNum = new HashMap<>();
        for (int i = 1, j = 7; i <= 7; i++) {
            LambdaQueryWrapper<Orders> ordersLambdaQueryWrapper = new LambdaQueryWrapper<>();
            ordersLambdaQueryWrapper.gt(Orders::getOrderTime, now.minusDays(i));//大于
            ordersLambdaQueryWrapper.lt(Orders::getOrderTime, now.minusDays(i-1));//小于
            sevenSalesNum.put(j--, ordersService.count(ordersLambdaQueryWrapper));
        }
//        System.out.println(sevenSalesNum);
        return new DishSalesDTO(dishMsg, categoryMsg, sevenSalesNum);
    }


    @Test

   void test(){
        LocalDateTime now = LocalDateTime.now();
        for (int i = 1, j = 7; i <= 7; i++) {
            System.out.println(now.minusDays(i));
            System.out.println(now.minusDays(i-1));
        }
   }
}





