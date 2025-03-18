package com.mannor.mealscoming.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mannor.mealscoming.entity.Evaluation;
import com.mannor.mealscoming.mapper.EvaluationMapper;
import com.mannor.mealscoming.service.EvaluationService;
import org.springframework.stereotype.Service;

@Service
public class EvaluationServiceImpl extends ServiceImpl<EvaluationMapper, Evaluation> implements EvaluationService {
}