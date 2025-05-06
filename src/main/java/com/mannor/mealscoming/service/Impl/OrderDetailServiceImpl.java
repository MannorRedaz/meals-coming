package com.mannor.mealscoming.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mannor.mealscoming.dto.DishSalesDTO;
import com.mannor.mealscoming.entity.*;
import com.mannor.mealscoming.mapper.OrderDetailMapper;
import com.mannor.mealscoming.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.w3c.dom.ls.LSOutput;
//import sun.security.pkcs11.wrapper.Functions;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

    @Autowired
    private OrdersService ordersService;


    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;
    @Autowired
    private EmployeeService employeeService;

    /**
     * 查询所有的菜品种类，对应菜品种类的售出份数
     *
     * @return
     */
    @Override
    public DishSalesDTO getDishSales(HttpServletRequest request) {
        Object merchantId = request.getSession().getAttribute("MerchantId");
        if (merchantId == null) {
            merchantId = request.getSession().getAttribute("EmployeeId");
            merchantId = employeeService.getOne(new LambdaQueryWrapper<Employee>().eq(Employee::getId, merchantId)).getMerchantId();
        }


        List<OrderDetail> orderDetails = this.getBaseMapper().selectList(new LambdaQueryWrapper<OrderDetail>());
        Map<String, Integer> dishMsg = new HashMap<>();
        orderDetails.forEach(item -> dishMsg.merge(item.getName(), item.getNumber(), Integer::sum));

        List<Category> categories = categoryService.getBaseMapper().selectList(new LambdaQueryWrapper<Category>());
        Map<String, Integer> categoryMsg = new HashMap<>();
        categories.forEach(item -> categoryMsg.put(item.getName(), 0));

        dishMsg.forEach((dishName, sales) -> {
            try {
                Dish dish = dishService.getOne(new LambdaQueryWrapper<Dish>().eq(Dish::getName, dishName));
                String categoryName = categoryService.getById(dish.getCategoryId()).getName();
                categoryMsg.merge(categoryName, sales, Integer::sum);
            } catch (NullPointerException e) {
                Setmeal setmeal = setmealService.getOne(new LambdaQueryWrapper<Setmeal>().eq(Setmeal::getName, dishName));
                String categoryName = categoryService.getById(setmeal.getCategoryId()).getName();
                categoryMsg.merge(categoryName, sales, Integer::sum);
            }
        });


        //查询近七天的数据
        LocalDateTime now = LocalDateTime.now();
        // 计算七天之前的日期时间
        HashMap<Integer, Integer> sevenSalesNum = new HashMap<>();
        for (int i = 1, j = 7; i <= 7; i++) {
            LambdaQueryWrapper<Orders> ordersLambdaQueryWrapper = new LambdaQueryWrapper<>();
            ordersLambdaQueryWrapper.gt(Orders::getOrderTime, now.minusDays(i));//大于
            ordersLambdaQueryWrapper.lt(Orders::getOrderTime, now.minusDays(i - 1));//小于
            sevenSalesNum.put(j--, ordersService.count(ordersLambdaQueryWrapper));
        }
//        System.out.println(sevenSalesNum);
        return new DishSalesDTO(dishMsg, categoryMsg, sevenSalesNum, null, null);
    }

    @Override
    public DishSalesDTO getAll() {
        List<OrderDetail> orderDetails = this.getBaseMapper().selectList(new LambdaQueryWrapper<OrderDetail>());
        Map<String, Integer> dishMsg = new HashMap<>();
        orderDetails.forEach(item -> dishMsg.merge(item.getName(), item.getNumber(), Integer::sum));

        List<Category> categories = categoryService.getBaseMapper().selectList(new LambdaQueryWrapper<Category>());
        Map<String, Integer> categoryMsg = new HashMap<>();
        categories.forEach(item -> categoryMsg.put(item.getName(), 0));

        dishMsg.forEach((dishName, sales) -> {
            try {
                Dish dish = dishService.getOne(new LambdaQueryWrapper<Dish>().eq(Dish::getName, dishName));
                String categoryName = categoryService.getById(dish.getCategoryId()).getName();
                categoryMsg.merge(categoryName, sales, Integer::sum);
            } catch (NullPointerException e) {
                Setmeal setmeal = setmealService.getOne(new LambdaQueryWrapper<Setmeal>().eq(Setmeal::getName, dishName));
                String categoryName = categoryService.getById(setmeal.getCategoryId()).getName();
                categoryMsg.merge(categoryName, sales, Integer::sum);
            }
        });


        //查询近七天的数据
        LocalDateTime now = LocalDateTime.now();
        // 计算七天之前的日期时间
        HashMap<Integer, Integer> sevenSalesNum = new HashMap<>();
        for (int i = 1, j = 7; i <= 7; i++) {
            LambdaQueryWrapper<Orders> ordersLambdaQueryWrapper = new LambdaQueryWrapper<>();
            ordersLambdaQueryWrapper.gt(Orders::getOrderTime, now.minusDays(i));//大于
            ordersLambdaQueryWrapper.lt(Orders::getOrderTime, now.minusDays(i - 1));//小于
            sevenSalesNum.put(j--, ordersService.count(ordersLambdaQueryWrapper));
        }
//        System.out.println(sevenSalesNum);
//        return new DishSalesDTO(dishMsg, categoryMsg, sevenSalesNum);
        return new DishSalesDTO(null, null, null, null, null);
    }

    @Override
    public DishSalesDTO getAll(Long merchantId, LocalDateTime createTimeEnd, LocalDateTime createTimeStart) {
        // 根据时间和merchantId条件查询订单
        LambdaQueryWrapper<Orders> ordersQueryWrapper = new LambdaQueryWrapper<>();
        if (createTimeStart != null && createTimeEnd != null) {
            ordersQueryWrapper.between(Orders::getOrderTime, createTimeStart, createTimeEnd);
        }
        if (merchantId != null) {
            ordersQueryWrapper.eq(Orders::getMerchantId, merchantId);
        }
        List<Orders> ordersList = ordersService.list(ordersQueryWrapper);

        // 根据订单查询订单详情
        LambdaQueryWrapper<OrderDetail> orderDetailQueryWrapper = new LambdaQueryWrapper<>();
        if (!ordersList.isEmpty()) {
            List<Long> orderIds = ordersList.stream().map(Orders::getId).collect(Collectors.toList());
            orderDetailQueryWrapper.in(OrderDetail::getOrderId, orderIds);
        }
        List<OrderDetail> orderDetails = this.getBaseMapper().selectList(orderDetailQueryWrapper);

        // 统计菜品销售信息
        Map<String, Integer> dishMsg = new HashMap<>();
        orderDetails.forEach(item -> dishMsg.merge(item.getName(), item.getNumber(), Integer::sum));

        // 统计菜品分类销售信息
        LambdaQueryWrapper<Category> categoryQueryWrapper = new LambdaQueryWrapper<>();
        if (merchantId != null) {
            categoryQueryWrapper.eq(Category::getMerchantId, merchantId);
        }
        List<Category> categories = categoryService.getBaseMapper().selectList(categoryQueryWrapper);
        Map<String, Integer> categoryMsg = new HashMap<>();
        categories.forEach(item -> categoryMsg.put(item.getName(), 0));

        dishMsg.forEach((dishName, sales) -> {
            try {
                Dish dish = dishService.getOne(new LambdaQueryWrapper<Dish>().eq(Dish::getName, dishName));
                String categoryName = categoryService.getById(dish.getCategoryId()).getName();
                categoryMsg.merge(categoryName, sales, Integer::sum);
            } catch (NullPointerException e) {
                Setmeal setmeal = setmealService.getOne(new LambdaQueryWrapper<Setmeal>().eq(Setmeal::getName, dishName));
                String categoryName = categoryService.getById(setmeal.getCategoryId()).getName();
                categoryMsg.merge(categoryName, sales, Integer::sum);
            }
        });

        // 查询近七天的数据，并根据merchantId进行筛选
        LocalDateTime now = LocalDateTime.now();
        HashMap<Integer, Integer> sevenSalesNum = new HashMap<>();
        for (int i = 1, j = 7; i <= 7; i++) {
            LambdaQueryWrapper<Orders> sevenDaysOrdersQueryWrapper = new LambdaQueryWrapper<>();
            sevenDaysOrdersQueryWrapper.gt(Orders::getOrderTime, now.minusDays(i));
            sevenDaysOrdersQueryWrapper.lt(Orders::getOrderTime, now.minusDays(i - 1));
            if (merchantId != null) {
                sevenDaysOrdersQueryWrapper.eq(Orders::getMerchantId, merchantId);
            }
            sevenSalesNum.put(j--, ordersService.count(sevenDaysOrdersQueryWrapper));
        }

        // 计算订单量，已经根据时间和merchantId筛选过
        Integer orderCount = ordersList.size();

        // 计算订单总金额，已经根据时间和merchantId筛选过
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (Orders order : ordersList) {
            totalAmount = totalAmount.add(order.getAmount());
        }

        return new DishSalesDTO(dishMsg, categoryMsg, sevenSalesNum, totalAmount, orderCount);
    }


}





