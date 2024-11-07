package com.mannor.mealscoming.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mannor.mealscoming.entity.Employee;
import com.mannor.mealscoming.mapper.EmployeeMapper;
import com.mannor.mealscoming.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiuceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

}
