package com.mannor.mealscoming.service.Impl;

import cn.hutool.core.lang.generator.SnowflakeGenerator;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mannor.mealscoming.dto.MerchantDto;
import com.mannor.mealscoming.entity.Merchant;
import com.mannor.mealscoming.entity.MerchantAudit;
import com.mannor.mealscoming.entity.MerchantDetails;
import com.mannor.mealscoming.mapper.MerchantMapper;
import com.mannor.mealscoming.service.MerchantService;
import com.mannor.mealscoming.service.MerchantAuditService;
import com.mannor.mealscoming.service.MerchantDetailsService;
import com.mannor.mealscoming.vo.MerchantVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.baomidou.mybatisplus.annotation.IdType.ASSIGN_ID;

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
            merchantVo.setMerchantName(merchant.getMerchantName());
            merchantVo.setCreateTime(merchant.getCreateTime());
            merchantVo.setUpdateTime(merchant.getUpdateTime());
            merchantVo.setMerchantDetails(merchantDetailsService.getOne(new LambdaQueryWrapper<MerchantDetails>().eq(MerchantDetails::getMerchantId, merchant.getId())));
            merchantVo.setMerchantAudit(merchantAuditService.getOne(new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<MerchantAudit>().eq(MerchantAudit::getMerchantId, merchant.getId())));
            merchantVos.add(merchantVo);
        });
        return merchantVos;
    }

    @Override
    @Transactional
    public Boolean register(MerchantVo merchantVo) {


        // 保存商家信息
        Merchant merchant = new Merchant();
        Long merchantId = new SnowflakeGenerator().next();
        merchant.setMerchantName(merchantVo.getMerchantName());
        merchant.setId(merchantId);
        merchant.setPassword(DigestUtils.md5DigestAsHex("12345".getBytes()));
        merchant.setCreateTime(LocalDateTime.now());
        merchant.setUpdateTime(LocalDateTime.now());
        this.save(merchant);

        // 保存商家详情
        MerchantDetails merchantDetails = merchantVo.getMerchantDetails();
        Long merchantDetailsId = new SnowflakeGenerator().next();
        merchantDetails.setId(merchantDetailsId);
        merchantDetails.setMerchantId(merchantId);
        System.out.println(merchantDetails);
        merchantDetailsService.save(merchantDetails);


        // 保存商家审核信息
        MerchantAudit merchantAudit = new MerchantAudit();
        merchantAudit.setId(new SnowflakeGenerator().next());
        merchantAudit.setMerchantId(merchantId);
        merchantAudit.setAuditStatus("未审核");
        merchantAudit.setAuditComment("");
        merchantAudit.setAuditTime(null);
        merchantAuditService.save(merchantAudit);

        return true;
    }

    @Override
    public Merchant getByAccountName(String accountName) {
        return merchantMapper.selectOne(new LambdaQueryWrapper<Merchant>().eq(Merchant::getUsername, accountName));
    }

    @Override
    public Boolean updateMerchantInfo(MerchantDto merchantDto) {
        // 更新商家信息
        Merchant merchant = new Merchant();
        merchant.setId(merchantDto.getId());
        merchant.setMerchantName(merchantDto.getMerchantName());
        merchant.setUsername(merchantDto.getUsername());
//        merchant.setPassword(DigestUtils.md5DigestAsHex(merchantDto.getPassword().getBytes()));
        merchant.setPassword(null);
        merchant.setUpdateTime(LocalDateTime.now());
        merchant.setUsername(merchantDto.getUsername());

        // 更新商家详情
        MerchantDetails merchantDetails = merchantDto.getMerchantDetails();

        return this.updateById(merchant)&&merchantDetailsService.updateById(merchantDetails);
    }


}