package com.mannor.mealscoming.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mannor.mealscoming.entity.CustomerComplaintSuggestion;
import com.mannor.mealscoming.mapper.CustomerComplaintSuggestionMapper;
import com.mannor.mealscoming.service.CustomerComplaintSuggestionService;
import org.springframework.stereotype.Service;

@Service
public class CustomerComplaintSuggestionServiceImpl extends ServiceImpl<CustomerComplaintSuggestionMapper, CustomerComplaintSuggestion> implements CustomerComplaintSuggestionService {
}