package com.mannor.reggie_take_out.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mannor.reggie_take_out.dto.DishSalesDTO;
import com.mannor.reggie_take_out.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
}
