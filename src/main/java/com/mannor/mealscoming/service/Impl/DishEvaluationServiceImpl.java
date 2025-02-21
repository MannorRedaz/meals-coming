package com.mannor.mealscoming.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mannor.mealscoming.entity.DishEvaluation;
import com.mannor.mealscoming.mapper.DishEvaluationMapper;
import com.mannor.mealscoming.service.DishEvaluationService;
import org.springframework.stereotype.Service;

@Service
public class DishEvaluationServiceImpl extends ServiceImpl<DishEvaluationMapper, DishEvaluation> implements DishEvaluationService {
}