package com.mannor.mealscoming.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mannor.mealscoming.entity.MerchantAudit;
import com.mannor.mealscoming.service.MerchantAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/merchantAudit")
public class MerchantAuditController {

    @Autowired
    private MerchantAuditService merchantAuditService;

    /**
     * 新增商家审核信息
     * @param merchantAudit 商家审核实体
     * @return 新增结果
     */
    @PostMapping
    public boolean save(@RequestBody MerchantAudit merchantAudit) {
        return merchantAuditService.save(merchantAudit);
    }

    /**
     * 删除商家审核信息
     * @param id 商家审核信息的主键
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return merchantAuditService.removeById(id);
    }

    /**
     * 修改商家审核信息
     * @param merchantAudit 商家审核实体
     * @return 修改结果
     */
    @PutMapping
    public boolean update(@RequestBody MerchantAudit merchantAudit) {
        return merchantAuditService.updateById(merchantAudit);
    }

    /**
     * 根据主键查询单个商家审核信息
     * @param id 商家审核信息的主键
     * @return 商家审核信息实体
     */
    @GetMapping("/{id}")
    public MerchantAudit getById(@PathVariable Long id) {
        return merchantAuditService.getById(id);
    }

    /**
     * 查询所有商家审核信息
     * @return 商家审核信息列表
     */
    @GetMapping
    public List<MerchantAudit> findAll() {
        return merchantAuditService.list();
    }

    /**
     * 分页查询商家审核信息
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return 分页后的商家审核信息
     */
    @GetMapping("/page")
    public Page<MerchantAudit> findPage(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize) {
        QueryWrapper<MerchantAudit> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return merchantAuditService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }
}