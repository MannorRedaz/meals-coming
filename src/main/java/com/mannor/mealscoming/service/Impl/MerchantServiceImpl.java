package com.mannor.mealscoming.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mannor.mealscoming.entity.Merchant;
import com.mannor.mealscoming.entity.MerchantAudit;
import com.mannor.mealscoming.mapper.MerchantMapper;
import com.mannor.mealscoming.service.MerchantService;
import com.mannor.mealscoming.service.MerchantAuditService;
import com.mannor.mealscoming.service.MerchantDetailsService;
import com.mannor.mealscoming.vo.MerchantVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MerchantServiceImpl extends ServiceImpl<MerchantMapper, Merchant> implements MerchantService {

    @Autowired
    private MerchantMapper merchantMapper;
    @Autowired
    private MerchantAuditService merchantAuditService;
    @Autowired
    private MerchantDetailsService merchantDetailsService;

    @Override
    public List<MerchantVo> page(Integer page, Integer pageSize, String merchantName, String auditStatus, String auditComment, LocalDateTime createTimeEnd, LocalDateTime createTimeStart, LocalDateTime updateTimeEnd, LocalDateTime updateTimeStart) {
        Page<Merchant> pageParam = new Page<>(page, pageSize);
        IPage<Merchant> merchantPage = merchantMapper.selectMerchantPage(pageParam, merchantName, auditStatus, auditComment, createTimeEnd, createTimeStart, updateTimeEnd, updateTimeStart);
        List<MerchantVo> merchantVos = new ArrayList<>();
        merchantPage.getRecords().forEach(merchant -> {
            MerchantVo merchantVo = new MerchantVo();
            merchantVo.setId(merchant.getId());
            merchantVo.setDetailId(merchant.getDetailId());
            merchantVo.setMerchantName(merchant.getMerchantName());
            merchantVo.setCreateTime(merchant.getCreateTime());
            merchantVo.setUpdateTime(merchant.getUpdateTime());
            merchantVo.setMerchantDetails(merchantDetailsService.getById(merchant.getDetailId()));
            merchantVo.setMerchantAudit(merchantAuditService.getOne(new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<MerchantAudit>().eq(MerchantAudit::getMerchantId, merchant.getId())));
            merchantVos.add(merchantVo);
        });
        return merchantVos;
    }
}