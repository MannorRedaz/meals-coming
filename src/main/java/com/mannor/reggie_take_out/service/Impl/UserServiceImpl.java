package com.mannor.reggie_take_out.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mannor.reggie_take_out.entity.User;
import com.mannor.reggie_take_out.mapper.UserMapper;
import com.mannor.reggie_take_out.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
