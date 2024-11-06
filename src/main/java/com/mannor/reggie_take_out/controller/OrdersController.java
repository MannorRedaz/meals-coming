package com.mannor.reggie_take_out.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mannor.reggie_take_out.common.R;
import com.mannor.reggie_take_out.entity.Orders;
import com.mannor.reggie_take_out.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@Slf4j
@RequestMapping("/order")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    /**
     * 用户端管理订单
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/userPage")
    public R<Page> userPage(Integer page, Integer pageSize) {
        log.info("订单的查询：page={},pagesize={}", page, pageSize);
        return ordersService.pageOrdersDto(page,pageSize);
    }

    /**
     * 管理端查询订单
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page<Orders>> page(int page, int pageSize, String number,
                                @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss") Date beginTime,
                                @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss") Date endTime) {
        log.info("订单分页查询：page={}，pageSize={}，number={},beginTime={},endTime={}", page, pageSize, number, beginTime, endTime);
        Page<Orders> ordersPage = ordersService.pageOrders(page, pageSize, number, beginTime, endTime);
        return R.success(ordersPage);
    }

    /**
     * 支付订单处理
     *
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders) {
        log.info("submit订单提交的参数：Orders={}", orders);
        ordersService.submit(orders);
        return R.success("下单成功！");
    }

    /**
     * 订单派送处理
     *
     * @param orders
     * @return
     */
    @PutMapping
    public R<String> send(@RequestBody Orders orders) {
        log.info("参数：{}", orders);
        ordersService.updateById(orders);
        return R.success("派送完成");
    }


}
