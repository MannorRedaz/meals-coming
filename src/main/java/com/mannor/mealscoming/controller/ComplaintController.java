package com.mannor.mealscoming.controller;

import cn.hutool.core.lang.generator.SnowflakeGenerator;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mannor.mealscoming.common.BaseContext;
import com.mannor.mealscoming.common.R;
import com.mannor.mealscoming.dto.ComplaintUserDto;
import com.mannor.mealscoming.entity.Complaint;
import com.mannor.mealscoming.entity.Employee;
import com.mannor.mealscoming.entity.Merchant;
import com.mannor.mealscoming.entity.User;
import com.mannor.mealscoming.service.ComplaintService;
import com.mannor.mealscoming.service.EmployeeService;
import com.mannor.mealscoming.service.MerchantService;
import com.mannor.mealscoming.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/complaint")
@Slf4j
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private UserService userService;

    @Autowired
    private MerchantService merchantService;

    /**
     * 新增投诉建议管理信息
     *
     * @param complaint 投诉建议管理实体
     * @return 新增结果
     */
    @PostMapping
    public R<Boolean> save(@RequestBody Complaint complaint,HttpServletRequest request) {
        complaint.setId(new SnowflakeGenerator().next());
        complaint.setHandlingStatus("未处理");
        complaint.setUserId(BaseContext.getCurrentId());
        return R.success(complaintService.save(complaint));
    }

    /**
     * 删除投诉建议管理信息
     *
     * @param id 投诉建议管理信息的主键
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public R<Boolean> delete(@PathVariable Long id) {
        return R.success(complaintService.removeById(id));
    }

    /**
     * 修改投诉建议管理信息
     *
     * @param complaint 投诉建议管理实体
     * @return 修改结果
     */
    @PutMapping
    public R<Boolean> update(@RequestBody Complaint complaint) {
        complaint.setHandlingTime(LocalDateTime.now());
        return R.success(complaintService.updateById(complaint));
    }

    /**
     * 根据主键查询单个投诉建议管理信息
     *
     * @param id 投诉建议管理信息的主键
     * @return 投诉建议管理信息实体
     */
    @GetMapping("/{id}")
    public R<Complaint> getById(@PathVariable Long id) {
        return R.success(complaintService.getById(id));
    }

    /**
     * 查询所有投诉建议管理信息
     *
     * @return 投诉建议管理信息列表
     */
    @GetMapping
    public R<Page<ComplaintUserDto>> findAll(@RequestParam Integer pageNum,
                                             @RequestParam Integer pageSize,
                                             @RequestParam(required = false) String userId,
                                             @RequestParam(required = false) String complaintType,
                                             @RequestParam(required = false) String handlingStatus) {
        if (pageNum <= 0 || pageSize <= 0) {
            throw new IllegalArgumentException("页码和每页数量必须为正整数");
        }
        QueryWrapper<Complaint> queryWrapper = new QueryWrapper<>();
        if (userId != null && !userId.isEmpty()) {
            queryWrapper.like("user_id", userId);
        }
        if (complaintType != null && !complaintType.isEmpty()) {
            queryWrapper.eq("complaint_type", complaintType);
        }
        if (handlingStatus != null && !handlingStatus.isEmpty()) {
            queryWrapper.eq("handling_status", handlingStatus);
        }
        queryWrapper.orderByDesc("id");
        Page<Complaint> complaintPage = complaintService.page(new Page<>(pageNum, pageSize), queryWrapper);

        Page<ComplaintUserDto> complaintUserDtoPage = new Page<>();
        BeanUtils.copyProperties(complaintPage, complaintUserDtoPage, "records");

        List<ComplaintUserDto> complaintUserDtoList = new ArrayList<>();
        for (Complaint complaint : complaintPage.getRecords()) {
            ComplaintUserDto complaintUserDto = new ComplaintUserDto();
            BeanUtils.copyProperties(complaint, complaintUserDto);
            Long userIdLong = complaint.getUserId();
            if (userIdLong != null) {
                User user = userService.getById(userIdLong);
                complaintUserDto.setUser(user);
            }
            Long merchantId = complaint.getMerchantId();
            if (merchantId != null) {
                Merchant merchant = merchantService.getById(merchantId);
                complaintUserDto.setMerchant(merchant);
            }
            complaintUserDtoList.add(complaintUserDto);
        }
        complaintUserDtoPage.setRecords(complaintUserDtoList);

        return R.success(complaintUserDtoPage);
    }

    /**
     * 分页查询投诉建议管理信息
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return 分页后的投诉建议管理信息
     */
    /**
     * 分页查询客户投诉建议信息
     *
     * @param pageNum        页码
     * @param pageSize       每页数量
     * @param userId         用户 ID
     * @param complaintType  投诉建议类型
     * @param handlingStatus 处理状态
     * @return 分页后的客户投诉建议信息
     */
    @GetMapping("/page")
    public R<Page<Complaint>> findPage(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam(required = false) String userId,
                                       @RequestParam(required = false) String complaintType,
                                       @RequestParam(required = false) String handlingStatus
            , HttpServletRequest request) {
        // 构造商家查询条件
        Object merchantId = request.getSession().getAttribute("MerchantId");
        if (merchantId == null) {
            merchantId = request.getSession().getAttribute("EmployeeId");
            merchantId = employeeService.getOne(new LambdaQueryWrapper<Employee>().eq(Employee::getId, merchantId)).getMerchantId();
        }

        if (pageNum <= 0 || pageSize <= 0) {
            throw new IllegalArgumentException("页码和每页数量必须为正整数");
        }
        QueryWrapper<Complaint> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("merchant_id", merchantId);
        if (userId != null && !userId.isEmpty()) {
            queryWrapper.eq("user_id", userId);
        }
        if (complaintType != null && !complaintType.isEmpty()) {
            queryWrapper.eq("complaint_type", complaintType);
        }
        if (handlingStatus != null && !handlingStatus.isEmpty()) {
            queryWrapper.eq("handling_status", handlingStatus);
        }
        queryWrapper.orderByDesc("id");
        return R.success(complaintService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }


    @GetMapping("/list")
    public R<List<Complaint>> list(HttpServletRequest request) {
        // 构造商家查询条件
        Object user = request.getSession().getAttribute("user");
        LambdaQueryWrapper<Complaint> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(user == null, Complaint::getUserId, user);
        queryWrapper.orderByDesc(Complaint::getHandlingTime);
        return R.success(complaintService.list(queryWrapper));
    }


}