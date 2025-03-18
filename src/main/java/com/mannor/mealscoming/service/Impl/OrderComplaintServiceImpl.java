package com.mannor.mealscoming.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mannor.mealscoming.entity.OrderComplaint;
import com.mannor.mealscoming.mapper.OrderComplaintMapper;
import com.mannor.mealscoming.service.OrderComplaintService;
import org.springframework.stereotype.Service;

@Service
public class OrderComplaintServiceImpl extends ServiceImpl<OrderComplaintMapper, OrderComplaint> implements OrderComplaintService {
}