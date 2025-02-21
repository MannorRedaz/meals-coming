package com.mannor.mealscoming.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mannor.mealscoming.entity.SuperAdmin;
import com.mannor.mealscoming.mapper.SuperAdminMapper;
import com.mannor.mealscoming.service.SuperAdminService;
import org.springframework.stereotype.Service;

@Service
public class SuperAdminServiceImpl extends ServiceImpl<SuperAdminMapper, SuperAdmin> implements  SuperAdminService{
}