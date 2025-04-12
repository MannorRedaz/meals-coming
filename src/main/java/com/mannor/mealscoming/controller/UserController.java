package com.mannor.mealscoming.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mannor.mealscoming.Utils.ValidateCodeUtils;
import com.mannor.mealscoming.common.BaseContext;
import com.mannor.mealscoming.common.R;
import com.mannor.mealscoming.entity.User;
import com.mannor.mealscoming.service.UserService;
import com.mannor.mealscoming.service.VisitSerive;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private VisitSerive visitSerive;

    /**
     * 发送短信验证码
     *
     * @param user
     * @param session
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {
        //获取手机号
        String phone = user.getPhone();
        if (StringUtils.isNotEmpty(phone)) {
            //获取用户名对应的手机号
            User user1 = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getPhone, phone));
            if (user1 != null && user1.getStatus() == 0) {
                return R.error("你的账号已被锁住，请联系管理员解冻");
            }

            //生成随机的6位验证码
            String code = ValidateCodeUtils.generateValidateCode(6).toString();
            log.info("code={}", code);
            //调用阿里云提供的短信服务API完成发送短信
//            SMSUtils.sendMessage("杨自强的博客", "SMS_462036405", phone, code);
            //需要将生成的验证码保存到Session
            //session.setAttribute(phone, code);
            //将生成的验证码报错到redis中，并且设置5分钟失效
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return R.success("短信验证码发送成功！");
        }
        return R.error("短信验证码发送失败！");
    }

    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session) {
        log.info("map={}", map);
        //获取手机号
        String phone = map.get("phone").toString();
        //获取验证码
        String code = map.get("code").toString();
        //从Session中获取保存的验证码
        //Object codeInSession = session.getAttribute(phone);
        //进行验证码的比对（页面提交的验证码和Session中保存的验证码比对)

        //从redis中获得缓存的验证码
        Object codeInSession = redisTemplate.opsForValue().get(phone);
        if (codeInSession != null && codeInSession.equals(code)) {
            // 如果能够比对成功，说明登录成功
            LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(User::getPhone, phone);
            User user = userService.getOne(lambdaQueryWrapper);
            if (user == null) {
                //判断当前手机号对应的用户是否为新用户，如果是新用户就自动完成注册
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user", user.getId());
            //如果用户登录成功那就删除redis中缓存的验证码
            redisTemplate.delete(phone);

            return R.success(user);
        }
        return R.error("登录失败，");
    }

    /**
     * 退出登录
     *
     * @param session
     * @return
     */
    @PostMapping("/logout")
    public R<String> exit(HttpSession session) {
        //1.清理session
        session.removeAttribute("user");
        return R.success("退出成功");
    }

    /**
     * 用于用户修改信息（回显）
     *
     * @return
     */
    @GetMapping("userInfo")
    public R<User> userInfo() {
        User user = userService.getById(BaseContext.getCurrentId());
        return R.success(user);
    }

    /**
     * 用户修改提交
     *
     * @param user
     * @return
     */
  /*  @PutMapping("userInfo")
    public R<String> update(User user) {
        log.info("用户修改提交参数：{}", user);
        return R.error("提交未成功");
    }*/

    /**
     * @param user
     * @return
     */
    @PutMapping("/userInfo")
    public R<String> update(@RequestBody User user) {
        log.info("用户修改提交参数：{}", user);
        return userService.updateById(user) ? R.success("提交成功") : R.error("提交未成功");
    }

    @GetMapping("/page")
    public R<Page<User>> page(@RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam(required = false) String name) {
        Page<User> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        if (name != null && !name.isEmpty()) {
            queryWrapper.like(User::getName, name);
        }
        return R.success(userService.page(pageInfo, queryWrapper));
    }


}
