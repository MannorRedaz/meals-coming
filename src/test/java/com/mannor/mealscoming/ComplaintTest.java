package com.mannor.mealscoming;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mannor.mealscoming.entity.Complaint;
import com.mannor.mealscoming.service.ComplaintService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ComplaintTest {
    @Autowired
    ComplaintService complaintService;
    @Test
    public void test() {


        QueryWrapper<Complaint> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        System.out.println(complaintService.page(new Page<>(1, 10), queryWrapper));
    }
}
