package com.mannor.mealscoming.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mannor.mealscoming.entity.MerchantAudit;
import com.mannor.mealscoming.mapper.MerchantAuditMapper;
import com.mannor.mealscoming.service.MerchantAuditService;
import org.springframework.stereotype.Service;

@Service
public class MerchantAuditServiceImpl extends ServiceImpl<MerchantAuditMapper, MerchantAudit> implements MerchantAuditService {
}