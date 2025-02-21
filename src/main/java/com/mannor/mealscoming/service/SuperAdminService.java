package com.mannor.mealscoming.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mannor.mealscoming.entity.SuperAdmin;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SuperAdminService  extends IService<SuperAdmin> {
}
