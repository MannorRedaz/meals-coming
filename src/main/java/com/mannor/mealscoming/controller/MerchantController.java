package com.mannor.mealscoming.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mannor.mealscoming.common.R;
import com.mannor.mealscoming.dto.MerchantDto;
import com.mannor.mealscoming.entity.Employee;
import com.mannor.mealscoming.entity.Merchant;
import com.mannor.mealscoming.entity.MerchantAudit;
import com.mannor.mealscoming.entity.MerchantDetails;
import com.mannor.mealscoming.service.EmployeeService;
import com.mannor.mealscoming.service.MerchantAuditService;
import com.mannor.mealscoming.service.MerchantDetailsService;
import com.mannor.mealscoming.service.MerchantService;
import com.mannor.mealscoming.vo.MerchantVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/merchant")
@Slf4j
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private MerchantDetailsService merchantDetailsService;

    @Autowired
    private MerchantAuditService merchantAuditService;

    @Autowired
    private EmployeeService employeeService;

    /**
     * 新增商家信息
     *
     * @param merchant 商家实体
     * @return 新增结果
     */
    @PostMapping
    public R<Boolean> save(@RequestBody Merchant merchant) {
        return R.success(merchantService.save(merchant));
    }

    /**
     * 删除商家信息
     *
     * @param id 商家信息的主键
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public R<Boolean> delete(@PathVariable Long id) {
        return R.success(merchantService.removeById(id));
    }

    /**
     * 修改商家信息
     *
     * @param merchant 商家实体
     * @return 修改结果
     */
    @PutMapping
    public R<Boolean> update(@RequestBody Merchant merchant) {
        return R.success(merchantService.updateById(merchant));
    }

    @PutMapping("info")
    public R<Boolean> putInfo(@RequestBody MerchantDto merchantDto) {
        log.info("修改商家信息：{}", merchantDto);

        return R.success( merchantService.updateMerchantInfo(merchantDto));
    }


    /**
     * 根据主键查询单个商家信息
     *
     * @param id 商家信息的主键
     * @return 商家信息实体
     */
    @GetMapping("/{id}")
    public R<Merchant> getById(@PathVariable Long id) {
        return R.success(merchantService.getById(id));
    }

    @GetMapping("detail/{id}")
    public R<MerchantDto> getByIdDetail(@PathVariable Long id) {
        Merchant merchant = merchantService.getById(id);
        MerchantDto merchantDto = new MerchantDto();
        merchantDto.setId(merchant.getId());
        merchantDto.setMerchantName(merchant.getMerchantName());
        merchantDto.setCreateTime(merchant.getCreateTime());
        merchantDto.setUpdateTime(merchant.getUpdateTime());
        merchantDto.setUsername(merchant.getUsername());
        merchantDto.setPassword(merchant.getPassword());
        MerchantDetails merchantDetails = merchantDetailsService.getOne(new LambdaQueryWrapper<MerchantDetails>().eq(MerchantDetails::getMerchantId, merchant.getId()));

        merchantDto.setMerchantDetails(merchantDetails);
        MerchantAudit merchantAudit = merchantAuditService.getOne(new LambdaQueryWrapper<MerchantAudit>().eq(MerchantAudit::getMerchantId, merchant.getId()));
        merchantDto.setMerchantAudit(merchantAudit);

        return R.success(merchantDto);
    }

    /**
     * 分页查询商家信息
     *
     * @param page         页码
     * @param pageSize     每页数量
     * @param merchantName 商家名称（可选）
     * @return 分页后的商家信息
     */
    @GetMapping("/page")
    public R<List<MerchantVo>> findPage(@RequestParam Integer page,
                                        @RequestParam Integer pageSize,
                                        @RequestParam(required = false) String merchantName,
                                        @RequestParam(required = false) String auditStatus,
                                        @RequestParam(required = false) String auditComment,
                                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime createTimeEnd,
                                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime createTimeStart,
                                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime updateTimeEnd,
                                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime updateTimeStart
    ) {
        return R.success(merchantService.page(page, pageSize, merchantName, auditStatus, auditComment, createTimeEnd, createTimeStart, updateTimeEnd, updateTimeStart));
//        return R.success(null);
    }


    /**
     * 注册商家
     *
     * @param merchantVo 商家信息
     * @return 注册结果
     */
    @PostMapping("register")
    public R<Boolean> register(@RequestBody MerchantVo merchantVo) {

        return R.success(merchantService.register(merchantVo));
    }

    /**
     * 商家及员工登录
     *
     * @param request
     * @param merchant 包含用户名和密码的商家或员工信息
     * @return 登录结果
     */
    @PostMapping("/login")
    public R<Object> login(HttpServletRequest request, @RequestBody Merchant merchant) {
        // 1. 将页面提交的密码`password`进行`md5`加密处理
        String password = merchant.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        // 2. 根据页面提交的用户名`username`查询数据库
        Merchant loggedMerchant = merchantService.getByAccountName(merchant.getUsername());

        //2. 根据页面提交的用户名`username`查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, merchant.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        boolean flag = false;

        // 3. 如果没有查询到则返回登录失败结果
        if (loggedMerchant == null) {
            if (emp == null) {
                return R.error("登录失败！");
            }
            flag = true;
        }


        if (!flag) {
            MerchantAudit one = merchantAuditService.getOne(new LambdaQueryWrapper<MerchantAudit>().eq(MerchantAudit::getMerchantId, loggedMerchant.getId()));

            if (!"已通过".equals(one.getAuditStatus())) {
                return R.error("账号状态异常，请联系管理员修改");
            }


            // 4. 密码比对，如果不一致则返回登录失败结果
            if (!password.equals(loggedMerchant.getPassword())) {
                return R.error("登录失败！");
            }
            // 6. 登录成功，将商家/员工`id`存入`Session`并返回登录成功结果
            request.getSession().setAttribute("MerchantId", loggedMerchant.getId());
            return R.success(loggedMerchant);
        } else {
            MerchantAudit one = merchantAuditService.getOne(new LambdaQueryWrapper<MerchantAudit>().eq(MerchantAudit::getMerchantId, emp.getMerchantId()));

            if (!"已通过".equals(one.getAuditStatus())) {
                return R.error("账号状态异常，请联系管理员修改");
            }
            //  4. 密码比对，如果不一致则返回登录失败结果
            if (!emp.getPassword().equals(password)) {
                return R.error("登录失败！");
            }
            //  5. 查看员工状态，如果为已禁用状态，则返回员工已禁用结果
            if (emp.getStatus() == 0) {
                return R.error("员工已被禁用！请切换账号或者商家账号登录");
            }
            //  6. 登录成功，将员工`id`存入`Session`并返回登录成功结果
            request.getSession().setAttribute("EmployeeId", emp.getId());
            return R.success(emp);
        }


    }

    /**
     * 退出登录
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> exit(HttpServletRequest request) {
        // 1. 清理 session
        try {
            request.getSession().removeAttribute("MerchantId");
            request.getSession().removeAttribute("EmployeeId");
        } catch (Exception e) {
            log.info("退出登录！");
        }

        return R.success("退出成功！");
    }

    @GetMapping("list")
    public R<List<Merchant>> list() {
        LambdaQueryWrapper<Merchant> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(Merchant::getStatus, 1);
        List<Merchant> list = merchantService.list(queryWrapper);
        return R.success(list);
    }


}