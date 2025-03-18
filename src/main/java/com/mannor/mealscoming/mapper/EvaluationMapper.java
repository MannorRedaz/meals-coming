package com.mannor.mealscoming.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mannor.mealscoming.entity.Evaluation;
import org.apache.ibatis.annotations.Mapper;

// 评价管理表 Mapper 接口
@Mapper
public interface EvaluationMapper extends BaseMapper<Evaluation> {
}
