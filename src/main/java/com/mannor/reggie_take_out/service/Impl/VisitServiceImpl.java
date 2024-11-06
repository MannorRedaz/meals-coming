package com.mannor.reggie_take_out.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mannor.reggie_take_out.entity.Visit;
import com.mannor.reggie_take_out.mapper.VisitMapper;
import com.mannor.reggie_take_out.service.VisitSerive;
import org.springframework.stereotype.Service;

@Service
public class VisitServiceImpl extends ServiceImpl<VisitMapper, Visit> implements VisitSerive {
}
