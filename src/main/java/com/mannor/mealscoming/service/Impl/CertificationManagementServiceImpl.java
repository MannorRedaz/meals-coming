package com.mannor.mealscoming.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mannor.mealscoming.entity.CertificationManagement;
import com.mannor.mealscoming.mapper.CertificationManagementMapper;
import com.mannor.mealscoming.service.CertificationManagementService;
import org.springframework.stereotype.Service;

@Service
public class CertificationManagementServiceImpl extends ServiceImpl<CertificationManagementMapper, CertificationManagement> implements CertificationManagementService {
}