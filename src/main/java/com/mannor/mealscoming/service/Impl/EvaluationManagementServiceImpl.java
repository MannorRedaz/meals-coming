package com.mannor.mealscoming.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mannor.mealscoming.entity.EvaluationManagement;
import com.mannor.mealscoming.mapper.EvaluationManagementMapper;
import com.mannor.mealscoming.service.EvaluationManagementService;
import org.springframework.stereotype.Service;

@Service
public class EvaluationManagementServiceImpl extends ServiceImpl<EvaluationManagementMapper, EvaluationManagement> implements EvaluationManagementService {
}