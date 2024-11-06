package com.mannor.reggie_take_out.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mannor.reggie_take_out.entity.Employee;
import com.mannor.reggie_take_out.mapper.EmployeeMapper;
import com.mannor.reggie_take_out.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiuceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

}
