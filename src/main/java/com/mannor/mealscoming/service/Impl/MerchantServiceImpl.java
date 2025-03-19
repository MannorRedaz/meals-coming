package com.mannor.mealscoming.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mannor.mealscoming.entity.Merchant;
import com.mannor.mealscoming.mapper.MerchantMapper;
import com.mannor.mealscoming.service.MerchantService;
import org.springframework.stereotype.Service;

@Service
public class MerchantServiceImpl extends ServiceImpl<MerchantMapper, Merchant> implements MerchantService {
}
