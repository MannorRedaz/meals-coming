package com.mannor.mealscoming.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mannor.mealscoming.common.R;
import com.mannor.mealscoming.entity.Orders;

import java.util.Date;

public interface OrdersService extends IService<Orders> {


    void submit(Orders orders);

    Page<Orders> pageOrders(int page, int pageSize, String number, Date beginTime, Date endTime);

    R<Page> pageOrdersDto(Integer page, Integer pageSize);
}
