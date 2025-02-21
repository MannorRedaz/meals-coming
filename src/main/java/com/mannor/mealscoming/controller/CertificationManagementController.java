package com.mannor.mealscoming.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mannor.mealscoming.entity.CertificationManagement;
import com.mannor.mealscoming.service.CertificationManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificationManagement")
public class CertificationManagementController {

    @Autowired
    private CertificationManagementService certificationManagementService;

    /**
     * 新增认证管理信息
     * @param certificationManagement 认证管理实体
     * @return 新增结果
     */
    @PostMapping
    public boolean save(@RequestBody CertificationManagement certificationManagement) {
        return certificationManagementService.save(certificationManagement);
    }

    /**
     * 删除认证管理信息
     * @param id 认证管理信息的主键
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return certificationManagementService.removeById(id);
    }

    /**
     * 修改认证管理信息
     * @param certificationManagement 认证管理实体
     * @return 修改结果
     */
    @PutMapping
    public boolean update(@RequestBody CertificationManagement certificationManagement) {
        return certificationManagementService.updateById(certificationManagement);
    }

    /**
     * 根据主键查询单个认证管理信息
     * @param id 认证管理信息的主键
     * @return 认证管理信息实体
     */
    @GetMapping("/{id}")
    public CertificationManagement getById(@PathVariable Long id) {
        return certificationManagementService.getById(id);
    }

    /**
     * 查询所有认证管理信息
     * @return 认证管理信息列表
     */
    @GetMapping
    public List<CertificationManagement> findAll() {
        return certificationManagementService.list();
    }

    /**
     * 分页查询认证管理信息
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return 分页后的认证管理信息
     */
    @GetMapping("/page")
    public Page<CertificationManagement> findPage(@RequestParam Integer pageNum,
                                                  @RequestParam Integer pageSize) {
        QueryWrapper<CertificationManagement> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return certificationManagementService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }
}