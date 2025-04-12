package com.mannor.mealscoming.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mannor.mealscoming.common.BaseContext;
import com.mannor.mealscoming.common.CustomException;
import com.mannor.mealscoming.common.R;
import com.mannor.mealscoming.dto.OrdersDto;
import com.mannor.mealscoming.entity.*;
import com.mannor.mealscoming.mapper.OrdersMapper;
import com.mannor.mealscoming.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {


    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressBookService addressBookService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private EmployeeService employeeService;

    /**
     * 支付订单处理
     *
     * @param orders
     */
    @Transactional
    public void submit(Orders orders) {
        //获得当前用户id
        Long userId = BaseContext.getCurrentId();
        //查询当前用户的购物车数据
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId, userId);
        List<ShoppingCart> shoppingCartList = shoppingCartService.list(lambdaQueryWrapper);
        if (shoppingCartList == null || shoppingCartList.size() == 0) {
            throw new CustomException("购物车为空无法下单");
        }
        //查询用户数据
        User user = userService.getById(userId);
        //查询地址数据
        Long addressBookId = orders.getAddressBookId();
        AddressBook addressBook = addressBookService.getById(addressBookId);
        if (addressBook == null) {
            throw new CustomException("用户地址信息有误，无法下单！");
        }
        //向订单表插入数据，一条数据
        long orderId = IdWorker.getId();//订单号

        AtomicInteger amount = new AtomicInteger(0);
        //AtomicInteger类是系统底层保护的int类型，通过对int类型的数据进行封装，提供执行方法的控制进行值的原子操作，但AtomicInteger ≠ Integer。
        List<OrderDetail> orderDetails = shoppingCartList.stream().map((item) -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setNumber(item.getNumber());
            orderDetail.setDishFlavor(item.getDishFlavor());
            orderDetail.setDishId(item.getDishId());
            orderDetail.setSetmealId(item.getSetmealId());
            orderDetail.setName(item.getName());
            orderDetail.setImage(item.getImage());
            orderDetail.setAmount(item.getAmount());
            amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
            //addAndGet：累加
            return orderDetail;
        }).collect(Collectors.toList());


        orders.setId(orderId);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
//        orders.setStatus(1);//设置订单状态为待付款，等支付成功回调函数执行，再修改为2
        orders.setStatus(2);//设置订单状态为待派送
        orders.setAmount(new BigDecimal(amount.get()));//总金额
        orders.setUserId(userId);
        orders.setNumber(String.valueOf(orderId));
        orders.setUserName(user.getName());
        orders.setConsignee(addressBook.getConsignee());
        orders.setPhone(addressBook.getPhone());
        orders.setAddress((addressBook.getProvinceName() == null ? "" : addressBook.getProvinceName())
                + (addressBook.getCityName() == null ? "" : addressBook.getCityName())
                + (addressBook.getDistrictName() == null ? "" : addressBook.getDistrictName())
                + (addressBook.getDetail() == null ? "" : addressBook.getDetail()));
        //向订单表插入数据，一条数据
        this.save(orders);

        //向订单明细表插入数据，多条数据
        orderDetailService.saveBatch(orderDetails);
        //清空购物车数据
        shoppingCartService.remove(lambdaQueryWrapper);

    }

    @Override
    public Page<Orders> pageOrders(int page, int pageSize, String number, Date beginTime, Date endTime, HttpServletRequest request) {
        // 根据以上信息进行分页查询。
        // 创建分页对象
        Page<Orders> pageInfo = new Page<>(page, pageSize);

        // 构造商家查询条件
        Object merchantId = request.getSession().getAttribute("MerchantId");
        if (merchantId == null) {
            merchantId = request.getSession().getAttribute("EmployeeId");

            Employee emp = employeeService.getOne(new LambdaQueryWrapper<Employee>().eq(merchantId == null, Employee::getId, merchantId));
            if (emp != null) {
                merchantId = emp.getMerchantId();
            }
        }
        // 创建查询条件对象。
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        if (merchantId != null)
            queryWrapper.eq(Orders::getMerchantId, merchantId);

        queryWrapper.like(StringUtils.isNotEmpty(number), Orders::getNumber, number);
        if (beginTime != null) {
            queryWrapper.between(Orders::getOrderTime, beginTime, endTime);
        }
        queryWrapper.orderByDesc(Orders::getOrderTime);
        ordersMapper.selectPage(pageInfo, queryWrapper);
        return pageInfo;
    }

    @Override
    public R<Page> pageOrdersDto(Integer page, Integer pageSize) {

        Page pageInfo = new Page(page, pageSize);
        Page ordersDtopageInfo = new Page<>();
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Orders::getOrderTime);
        queryWrapper.eq(Orders::getUserId, BaseContext.getCurrentId());//过滤掉不是当前用户的订单
        ordersService.page(pageInfo, queryWrapper);
        BeanUtils.copyProperties(pageInfo, ordersDtopageInfo, "records");//第三个参数是Page对象中忽略的属性，我们需要自己设置该属性
        //将pageInfo（orders数据）封装到->ordersDto->返回给前端
        List<Orders> records = pageInfo.getRecords();

        List<OrdersDto> list = records.stream().map(item -> {
            OrdersDto ordersDto = new OrdersDto();
            BeanUtils.copyProperties(item, ordersDto);//拷贝对象
            LambdaQueryWrapper<OrderDetail> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(OrderDetail::getOrderId, item.getNumber());
            int sumNum = orderDetailService.count(lambdaQueryWrapper);
            ordersDto.setSumNum(String.valueOf(sumNum));
            return ordersDto;
        }).collect(Collectors.toList());

        ordersDtopageInfo.setRecords(list);


        return R.success(ordersDtopageInfo);
    }
}
