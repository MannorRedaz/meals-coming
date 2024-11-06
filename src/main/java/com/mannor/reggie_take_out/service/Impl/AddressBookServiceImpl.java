package com.mannor.reggie_take_out.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mannor.reggie_take_out.entity.AddressBook;
import com.mannor.reggie_take_out.mapper.AddressBookMapper;
import com.mannor.reggie_take_out.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
