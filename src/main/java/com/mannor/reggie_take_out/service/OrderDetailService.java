package com.mannor.reggie_take_out.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mannor.reggie_take_out.dto.DishSalesDTO;
import com.mannor.reggie_take_out.entity.OrderDetail;

import java.util.List;
import java.util.Map;

public interface OrderDetailService extends IService<OrderDetail> {
     DishSalesDTO getDishSales();
}
