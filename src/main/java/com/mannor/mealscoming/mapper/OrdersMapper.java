package com.mannor.mealscoming.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mannor.mealscoming.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrdersMapper  extends BaseMapper<Orders> {
}
