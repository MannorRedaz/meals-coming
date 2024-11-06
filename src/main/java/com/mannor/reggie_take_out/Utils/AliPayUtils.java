package com.mannor.reggie_take_out.Utils;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mannor.reggie_take_out.entity.Orders;
import com.mannor.reggie_take_out.mapper.OrdersMapper;
import lombok.extern.slf4j.Slf4j;


import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class AliPayUtils {

    private static String privateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDk4luG4O28SgoZ7cY8yFXI8zxB0PtmSTSU2TdAFL/VnLszUfPPWXI6ZXgTIHPvvr7WaVKh0on3R7RGDxjVBI1ltzek7aMyu3U8twarpxPvExPCV/2lvnuHeCK9B+MEQ5URwCZd4RHp55Nr/rqU9moKCzLA+LoA5GWvh0tTCrVQhJboUc5jLPQLyMe2M3U1oP6qQqjlMpzA9GefdGHdbH+KvoSWMX+fPOZbJx1OO7uVGDZBiLMmteJxGGmYV4zB4KWkVIGyCiw07SGicxHlRP1X07Lp1XxI3GRTcFkmBn4j0MiiLrls0b649PIdOobJtogKQ6BBtKQiutWMOPz62HrdAgMBAAECggEAcqSWZ5aePWNwb9BTLPrDTSXYFrdQVMlyFuQ98lDyTVJ1yZzQ3BjXSDyuyhXFPF50Q/z5MKi+BifaOgUx0PC4yivdKTUGO1D9xe1+39eN+n+5s+xS1gP058Pa0ofyw53+OqfeD8O+L0SY6MzsM9TBBErbnyB2OiAsdqDzr6J7ctokHIlrp4zuvtOoexZ64CD6hnOGxUg2aS4nHl8uzchxy8zCLnYQ6CKG0e4BY10/P2W7o/k5G6djzSTl/g97hKsLLZnjwGOJmSmMtGXGXVvZiLOZuSoO4XGF+Qm4y5VGEVQQIykZQWckHd4UMpZAqxVsSE8UUlcyirByaN6/Ii1imQKBgQD2bohBvrzT4wZVYmoisihQSyCULGtyyEQPQ8Aqh/k+DGiUFTvd7CDNE1IxFtqzPajJl6uPqoOjkw6c90kHSNWzMk2kt/wint6wdpUbJW24F1gclBlx4mDSsI9sEkLC4Zo5+IBImly6eVkBG8vEwjm2Dn+F/JLw2W76RY/3PNqh4wKBgQDtxWg4XK923fOJd88T+pb/U5c1NHEpjheFAmwRrJVG62Nqb0Um7lZk9KD5HsrnJTA7ONqedre7eIJntN6YCFTlPrf6BFbMpEW0ZYLifsBV29rqSim4nIpEJN6icvh0EUR5bNuHjya4r9SzKCBTDxkwZYV1ZQA4sRLa6DGD4vwMPwKBgAD1dyRCWLu+U2T7qAwQg2WklTDeZ2Cf9NvWWKYRt1+Nbs68smnvpEUIATbdruYppEF8jJdXg4Gcbd8gezFDEEnNNU4hxSJhWd5356aeY6nmtVoPHXY6+kN3mmdCPu5BMHdvRxiBOUEkfbsqX4WZd94EemyMCgpT3kGtCZ1dqEAfAoGAVj/en1tXttmqRvpz3n5vGa1b00vzpKxLcc9AHmUpPTGTvX8haY7ZPgPTBKsb1R8TUNg9zW+pFFMRksb0W9fh28qVeBbs2YzZUCfmg3yRDlQTJBvE6Je4PCHXtLvZzliZKybGt3TBCguWL2rl1ttKOtkIwV3i562r9kZ196512YcCgYBuqamVIP+lG6WZlkF3/U3j1xM+uCoB0G8up/ePY/+y6U9nqGISDpkLNE8s3hc1U2cL3wdBunain53kmjbD/li+36z6j1sNWLi+vpMV5sDAbnDcSIGnlSgOVdHHrClZ9UdXttMO4D6MHup/+7koLGcN1gCYsNqTXyETCqbtCmMTqA==";
    private static String alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA5OJbhuDtvEoKGe3GPMhVyPM8QdD7Zkk0lNk3QBS/1Zy7M1Hzz1lyOmV4EyBz776+1mlSodKJ90e0Rg8Y1QSNZbc3pO2jMrt1PLcGq6cT7xMTwlf9pb57h3givQfjBEOVEcAmXeER6eeTa/66lPZqCgsywPi6AORlr4dLUwq1UISW6FHOYyz0C8jHtjN1NaD+qkKo5TKcwPRnn3Rh3Wx/ir6EljF/nzzmWycdTju7lRg2QYizJrXicRhpmFeMweClpFSBsgosNO0honMR5UT9V9Oy6dV8SNxkU3BZJgZ+I9DIoi65bNG+uPTyHTqGybaICkOgQbSkIrrVjDj8+th63QIDAQAB";
    private static String appId = "9021000132697847";
    private static String sellerId = "2088721024612384";
    private static String notify = "http://tugpbt.natappfree.cc/alipay/notify"; //回调通知订单的地址
    private static String returnUrl = "http://localhost:8080/front/page/pay-success.html"; //支付成功之后回调的地址


//    @Autowired  //Spring不能注入静态字段
//    private static OrdersMapper ordersMapper;



    private OrdersMapper ordersMapper;

    public AliPayUtils(OrdersMapper ordersMapper) {
        this.ordersMapper = ordersMapper;
    }

    /**
     * @param outTradeNo  订单号
     * @param totalAmount 金额
     * @param subject     商品名
     * @return AlipayTradeWapPayResponse 发起调用的响应结果
     * @throws AlipayApiException
     */


    public static AlipayTradeWapPayResponse pay(String outTradeNo, String totalAmount, String subject) throws AlipayApiException {
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setServerUrl("https://openapi-sandbox.dl.alipaydev.com/gateway.do");
        alipayConfig.setAppId(appId); //设置商家Appid
        alipayConfig.setPrivateKey(privateKey);
        alipayConfig.setFormat("json");
        alipayConfig.setAlipayPublicKey(alipayPublicKey);
        alipayConfig.setCharset("UTF-8");
        alipayConfig.setSignType("RSA2");
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setOutTradeNo(outTradeNo);//订单号outTrade
        model.setTotalAmount(totalAmount);//订单金额totalAmount
        model.setSubject(subject);//商品名subject
        model.setProductCode("QUICK_WAP_WAY");
        model.setSellerId(sellerId);//商家id号
        request.setBizModel(model);
        request.setNotifyUrl(notify);//设置异步回调
        request.setReturnUrl(returnUrl);//设置返回的页面-->支付成功
        AlipayTradeWapPayResponse response = alipayClient.pageExecute(request);
//        System.out.println(response.getBody());
//        System.out.println("===========response=========");
        if (response.isSuccess()) {
//            System.out.println("支付调用成功");
            log.info("支付宝支付调用成功");
        } else {
//            System.out.println("支付调用失败");
            // sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
            // String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
            // System.out.println(diagnosisUrl);
            log.info("支付宝支付调用失败");
        }
        return response;
    }

    /**
     * 返回为1则调用成功，0失败
     *
     * @param outTradeNo 订单号
     * @return
     * @throws AlipayApiException
     */
    public static int query(String outTradeNo) throws AlipayApiException {
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setServerUrl("https://openapi-sandbox.dl.alipaydev.com/gateway.do");
        alipayConfig.setAppId(appId);
        alipayConfig.setPrivateKey(privateKey);
        alipayConfig.setFormat("json");
        alipayConfig.setAlipayPublicKey(alipayPublicKey);
        alipayConfig.setCharset("UTF-8");
        alipayConfig.setSignType("RSA2");
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setOutTradeNo(outTradeNo);  //outTradeNo订单号
        request.setBizModel(model);
        AlipayTradeQueryResponse response = alipayClient.execute(request);
        System.out.println(response.getBody());
        if (response.isSuccess()) {
            System.out.println("支付状态查询调用成功");
            if ("TRADE_SUCCESS".equals(response.getTradeStatus())) {
                log.info("订单：{} 支付成功", outTradeNo);
                return 1;
            } else {
                log.info("订单：{} 支付失败", outTradeNo);
                return 0;
            }
        } else {
            log.info("订单：{} 发起查询支付情况失败", outTradeNo);
            return 0;
        }
    }

    /**
     * 设置回调通知
     *
     * @param request
     * @return
     * @throws Exception
     */
    public String notify(HttpServletRequest request) throws Exception {
        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
            System.out.println("=========支付宝异步回调========");

            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
            }
            String outTradeNo = params.get("out_trade_no");
            String gmtPayment = params.get("gmt_payment");
            String alipayTradeNo = params.get("trade_no");

            String sign = params.get("sign");
            String content = AlipaySignature.getSignCheckContentV1(params);
            boolean checkSignature = AlipaySignature.rsa256CheckContent(content, sign, alipayPublicKey, "UTF-8"); // 验证签名
            // 支付宝验签
            if (checkSignature) {
                // 验签通过
//                System.out.println("交易名称: " + params.get("subject"));
//                System.out.println("交易状态: " + params.get("trade_status"));
//                System.out.println("支付宝交易凭证号: " + params.get("trade_no"));
//                System.out.println("商户订单号: " + params.get("out_trade_no"));
//                System.out.println("交易金额: " + params.get("total_amount"));
//                System.out.println("买家在支付宝唯一id: " + params.get("buyer_id"));
//                System.out.println("买家付款时间: " + params.get("gmt_payment"));
//                System.out.println("买家付款金额: " + params.get("buyer_pay_amount"));
                // 查询订单并更新支付状态
                QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("number", outTradeNo);
                Orders order = ordersMapper.selectById(outTradeNo);
                order.setStatus(2);
                //设置LocalDateTime并与之匹配
                LocalDateTime checkoutTime = LocalDateTime.parse(gmtPayment, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                order.setCheckoutTime(checkoutTime);
                ordersMapper.updateById(order);
            }
        }
        return "success";
    }
}

