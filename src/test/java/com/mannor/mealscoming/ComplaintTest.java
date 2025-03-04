package com.mannor.mealscoming;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mannor.mealscoming.entity.ComplaintSuggestionManagement;
import com.mannor.mealscoming.service.ComplaintSuggestionManagementService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ComplaintTest {
    @Autowired
    ComplaintSuggestionManagementService complaintSuggestionManagementService;
    @Test
    public void test() {


        QueryWrapper<ComplaintSuggestionManagement> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        System.out.println(complaintSuggestionManagementService.page(new Page<>(1, 10), queryWrapper));
    }
}
