package com.mannor.mealscoming.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mannor.mealscoming.common.R;
import com.mannor.mealscoming.entity.SuperAdmin;
import com.mannor.mealscoming.service.SuperAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/admin")
public class SuperAdminController {

    @Autowired
    private SuperAdminService superAdminService;

    @PostMapping("/login")
    public R<SuperAdmin> login(@RequestBody Map<String, Object> map, HttpSession session) {
        log.info("map={}", map);
        // 获取用户名
        String username = (String) map.get("username");
        // 获取密码
        String password = (String) map.get("password");

        // 检查用户名和密码是否为空
        if (username == null || password == null) {
            return R.error("用户名或密码不能为空");
        }

        // 根据用户名查询管理员信息
        LambdaQueryWrapper<SuperAdmin> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SuperAdmin::getUsername, username);
        SuperAdmin admin = superAdminService.getOne(lambdaQueryWrapper);

        // 检查管理员信息是否存在
        if (admin == null) {
            return R.error("用户名不存在");
        }

        // 检查密码是否匹配
        if (!password.equals(admin.getPassword())) {
            return R.error("密码错误");
        }

        // 登录成功，将管理员 ID 存入 session
        session.setAttribute("admin", admin.getId());
        log.info("登录成功，返回管理员 ID：{}", admin.getId());

        // 将管理员信息中的密码字段设置为 null，以避免在返回给客户端时暴露密码信息
        admin.setPassword(null);
        return R.success(admin);
    }

    /**
     * 退出登录
     *
     * @param session
     * @return
     */
    @PostMapping("/logout")
    public R<String> exit(HttpSession session) {
        // 清理 session
        session.removeAttribute("admin");
        return R.success("退出成功");
    }
}