package com.mannor.mealscoming.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mannor.mealscoming.entity.Merchant;
import com.mannor.mealscoming.vo.MerchantVo;

import java.time.LocalDateTime;
import java.util.List;

public interface MerchantService extends IService<Merchant> {
    List<MerchantVo> page(Integer page, Integer pageSize, String merchantName, String auditStatus, String auditComment, LocalDateTime createTimeEnd, LocalDateTime createTimeStart, LocalDateTime updateTimeEnd, LocalDateTime updateTimeStart);
    Boolean register(MerchantVo merchantVo);
}
