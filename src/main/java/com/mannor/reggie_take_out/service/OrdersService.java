package com.mannor.reggie_take_out.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mannor.reggie_take_out.common.R;
import com.mannor.reggie_take_out.dto.OrdersDto;
import com.mannor.reggie_take_out.entity.Orders;

import java.util.Date;

public interface OrdersService extends IService<Orders> {


    void submit(Orders orders);

    Page<Orders> pageOrders(int page, int pageSize, String number, Date beginTime, Date endTime);

    R<Page> pageOrdersDto(Integer page, Integer pageSize);
}
