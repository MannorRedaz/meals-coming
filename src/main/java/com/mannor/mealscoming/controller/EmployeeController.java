package com.mannor.mealscoming.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mannor.mealscoming.common.BaseContext;
import com.mannor.mealscoming.common.R;
import com.mannor.mealscoming.entity.Employee;
import com.mannor.mealscoming.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    /**
     * 员工登录
     *
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {

        //1. 将页面提交的密码`password`进行`md5`加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //2. 根据页面提交的用户名`username`查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);
        //  3. 如果没有查询到则返回登录失败结果
        if (emp == null) {
            return R.error("登录失败！");
        }
        //  4. 密码比对，如果不一致则返回登录失败结果
        if (!emp.getPassword().equals(password)) {
            return R.error("登录失败！");
        }
        //  5. 查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if (emp.getStatus() == 0) {
            return R.error("员工已被禁用！");
        }
        //  6. 登录成功，将员工`id`存入`Session`并返回登录成功结果
        request.getSession().setAttribute("EmployeeId", emp.getId());
        return R.success(emp);
    }

    /**
     * 退出登录
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> exit(HttpServletRequest request) {
        //1.清理session
        request.getSession().removeAttribute("EmployeeId");
        return R.success("退出成功！");
    }

    /**
     * 新增员工
     *
     * @param employee
     * @return
     */
    @PostMapping()
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("新增员工接收的参数：{}", employee);
        //md5加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));//初始密码
        //将创建人时间，更新人时间进行设置--->由于我们设置了元数据处理器，在处理器上进行数据的处理，所以我们注释下列代码
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//        Long empId = (Long) request.getSession().getAttribute("EmployeeId");  //获取用户Id
//        employee.setCreateUser(empId);
//        employee.setUpdateUser(empId);
        log.info("新增员工保存的参数：{}", employee);
        Long userCurrentId = BaseContext.getCurrentId();
        Employee empById = employeeService.getById(userCurrentId);
        if (!"admin".equals(empById.getUsername())) {
            return R.error("您不是管理员，不能添加员工！");
        }
        // 操作service层
        employeeService.save(employee);
        return R.success("添加成功");
    }

    /**
     * 信息分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(Integer page, Integer pageSize, String name) {
        log.info("员工分页查询的参数：page:{},pageSize:{},name:{}", page, pageSize, name);
        //构造分页构造器
        Page pageInfo = new Page(page, pageSize);
        //条件限定
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);//姓名的分页查询
        queryWrapper.orderByDesc(Employee::getId);//排序
        //执行
        employeeService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 根据员工id修改员工账号
     *
     * @param employee
     * @return
     */
    @PutMapping()
    public R<String> Update(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("员工状态的设置参数：{}", employee);
        //-->由于我们设置了元数据处理器，在处理器上进行数据的处理，所以我们注释下列代码
        //employee.setUpdateTime(LocalDateTime.now());
        //Long empId = (Long) request.getSession().getAttribute("EmployeeId");
        //employee.setUpdateUser(empId);

        //只有管理员权限才能添加员工
        Long userCurrentId = BaseContext.getCurrentId();
        Employee empById = employeeService.getById(userCurrentId);
        if (!"admin".equals(empById.getUsername())&&!empById.getUsername().equals(employee.getUsername())) {
            return R.error("您不能修改管理员或其他员工的信息！");
        }
        employeeService.updateById(employee);
        return R.success("员工状态修改成功");
    }


    /**
     * 员工编辑时的页面回显
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> echo(@PathVariable Long id) {
        log.info("页面回显请求参数：{}", id);
        Employee emp = employeeService.getById(id);
        return emp != null ? R.success(emp) : R.error("没有查询到对应员工的信息");
    }

}
