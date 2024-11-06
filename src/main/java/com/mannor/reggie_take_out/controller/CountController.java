package com.mannor.reggie_take_out.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mannor.reggie_take_out.common.R;
import com.mannor.reggie_take_out.dto.DishSalesDTO;
import com.mannor.reggie_take_out.entity.Category;
import com.mannor.reggie_take_out.entity.OrderDetail;
import com.mannor.reggie_take_out.service.CategoryService;
import com.mannor.reggie_take_out.service.OrderDetailService;
import com.mannor.reggie_take_out.service.VisitSerive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@Slf4j
@RequestMapping("/statistic")
public class CountController {


    @Autowired
    private OrderDetailService orderDetailService;


    /**
     * 数据统计的查询返回,返回各个订单中的菜品名，和菜品销售数量
     *
     * @return
     */
    @GetMapping()
    public R<DishSalesDTO> getDishSales() {
        return R.success(orderDetailService.getDishSales());
    }

}
