package com.mannor.mealscoming.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mannor.mealscoming.entity.Complaint;
import com.mannor.mealscoming.mapper.ComplaintMapper;
import com.mannor.mealscoming.service.ComplaintService;
import org.springframework.stereotype.Service;

@Service
public class ComplaintServiceImpl extends ServiceImpl<ComplaintMapper, Complaint> implements ComplaintService {
}