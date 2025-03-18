package com.mannor.mealscoming.service.Impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mannor.mealscoming.entity.Certification;
import com.mannor.mealscoming.mapper.CertificationMapper;
import com.mannor.mealscoming.service.CertificationService;
import org.springframework.stereotype.Service;

@Service
public class CertificationServiceImpl extends ServiceImpl<CertificationMapper, Certification> implements CertificationService {
}