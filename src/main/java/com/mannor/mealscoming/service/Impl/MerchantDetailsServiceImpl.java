package com.mannor.mealscoming.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mannor.mealscoming.entity.MerchantDetails;
import com.mannor.mealscoming.mapper.MerchantDetailsMapper;
import com.mannor.mealscoming.service.MerchantDetailsService;
import org.springframework.stereotype.Service;

@Service
public class MerchantDetailsServiceImpl extends ServiceImpl<MerchantDetailsMapper, MerchantDetails> implements MerchantDetailsService {
}
