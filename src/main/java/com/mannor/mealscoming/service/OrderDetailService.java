package com.mannor.mealscoming.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mannor.mealscoming.dto.DishSalesDTO;
import com.mannor.mealscoming.entity.OrderDetail;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

public interface OrderDetailService extends IService<OrderDetail> {
    DishSalesDTO getDishSales(HttpServletRequest request);

    DishSalesDTO getAll();

    DishSalesDTO getAll(Long choose, LocalDateTime createTimeEnd, LocalDateTime createTimeStart);
}
