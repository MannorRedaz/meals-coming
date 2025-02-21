package com.mannor.mealscoming;

import com.mannor.mealscoming.entity.SuperAdmin;
import com.mannor.mealscoming.mapper.SuperAdminMapper;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SuperAdminTest {


    @Autowired
    private SuperAdminMapper superAdminMapper;
    @Test
    public void superAdminTest(){
        SuperAdmin superAdmin = new SuperAdmin();
        superAdmin.setUsername("mannor");
        superAdmin.setPassword("123456");
        superAdminMapper.insert(superAdmin);

    }
}
