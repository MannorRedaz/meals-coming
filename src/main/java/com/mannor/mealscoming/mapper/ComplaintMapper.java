package com.mannor.mealscoming.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mannor.mealscoming.entity.Complaint;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ComplaintMapper extends BaseMapper<Complaint> {
}