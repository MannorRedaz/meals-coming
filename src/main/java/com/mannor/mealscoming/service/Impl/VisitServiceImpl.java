package com.mannor.mealscoming.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mannor.mealscoming.entity.Visit;
import com.mannor.mealscoming.mapper.VisitMapper;
import com.mannor.mealscoming.service.VisitSerive;
import org.springframework.stereotype.Service;

@Service
public class VisitServiceImpl extends ServiceImpl<VisitMapper, Visit> implements VisitSerive {
}
