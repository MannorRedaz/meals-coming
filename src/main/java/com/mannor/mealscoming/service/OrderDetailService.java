package com.mannor.mealscoming.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mannor.mealscoming.dto.DishSalesDTO;
import com.mannor.mealscoming.entity.OrderDetail;

public interface OrderDetailService extends IService<OrderDetail> {
     DishSalesDTO getDishSales();
}
