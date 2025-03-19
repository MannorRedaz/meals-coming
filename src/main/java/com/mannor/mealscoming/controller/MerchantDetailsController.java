package com.mannor.mealscoming.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mannor.mealscoming.entity.MerchantDetails;
import com.mannor.mealscoming.service.MerchantDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/merchantDetails")
public class MerchantDetailsController {

    @Autowired
    private MerchantDetailsService merchantDetailsService;

    /**
     * 新增商家详情信息
     * @param merchantDetails 商家详情实体
     * @return 新增结果
     */
    @PostMapping
    public boolean save(@RequestBody MerchantDetails merchantDetails) {
        return merchantDetailsService.save(merchantDetails);
    }

    /**
     * 删除商家详情信息
     * @param id 商家详情信息的主键
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return merchantDetailsService.removeById(id);
    }

    /**
     * 修改商家详情信息
     * @param merchantDetails 商家详情实体
     * @return 修改结果
     */
    @PutMapping
    public boolean update(@RequestBody MerchantDetails merchantDetails) {
        return merchantDetailsService.updateById(merchantDetails);
    }

    /**
     * 根据主键查询单个商家详情信息
     * @param id 商家详情信息的主键
     * @return 商家详情信息实体
     */
    @GetMapping("/{id}")
    public MerchantDetails getById(@PathVariable Long id) {
        return merchantDetailsService.getById(id);
    }

    /**
     * 查询所有商家详情信息
     * @return 商家详情信息列表
     */
    @GetMapping
    public List<MerchantDetails> findAll() {
        return merchantDetailsService.list();
    }

    /**
     * 分页查询商家详情信息
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return 分页后的商家详情信息
     */
    @GetMapping("/page")
    public Page<MerchantDetails> findPage(@RequestParam Integer pageNum,
                                          @RequestParam Integer pageSize) {
        QueryWrapper<MerchantDetails> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return merchantDetailsService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }
}    