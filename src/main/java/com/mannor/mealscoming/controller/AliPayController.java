package com.mannor.mealscoming.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mannor.mealscoming.Utils.AliPayUtils;
import com.mannor.mealscoming.common.R;
import com.mannor.mealscoming.entity.Orders;
import com.mannor.mealscoming.mapper.OrdersMapper;
import com.mannor.mealscoming.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/alipay")
public class AliPayController {

    @Autowired
    private OrdersService ordersService;

    private Orders orders;
    private String outTradeNo;
    private String totalAmount;
    private String subject = "外卖订单支付";

    /**
     * 支付发起
     *
     * @return
     * @throws AlipayApiException
     */
    @GetMapping("/pay")
    public R<AlipayTradeWapPayResponse> pay() throws AlipayApiException {
        //查询最新订单的参数
        orders = ordersService.getOne(new LambdaQueryWrapper<Orders>().orderByDesc(Orders::getOrderTime).last("LIMIT 1"));
        outTradeNo = orders.getNumber();
        totalAmount = String.valueOf(orders.getAmount());
        return R.success(AliPayUtils.pay(outTradeNo, totalAmount, subject));
    }

    /**
     * 查询支付是否成功 0失败 1成功
     *
     * @return
     * @throws AlipayApiException
     */
    @GetMapping("/query")
    public R<Integer> query() throws AlipayApiException {
        int query = AliPayUtils.query(outTradeNo);
        return query == 1 ? R.success(1) : R.error("订单未支付");
    }


    /**
     * 回调地址,通知服务器支付成功
     *
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/notify")  // 注意这里必须是POST接口
    public String payNotify(HttpServletRequest request) throws Exception {
        AliPayUtils aliPayUtils = new AliPayUtils((OrdersMapper) ordersService.getBaseMapper());
        return  aliPayUtils.notify(request);
    }
}
