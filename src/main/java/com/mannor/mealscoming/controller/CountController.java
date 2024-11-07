package com.mannor.mealscoming.controller;

import com.mannor.mealscoming.common.R;
import com.mannor.mealscoming.dto.DishSalesDTO;
import com.mannor.mealscoming.service.OrderDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
