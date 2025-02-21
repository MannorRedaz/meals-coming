package com.mannor.mealscoming.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mannor.mealscoming.entity.CustomerComplaintSuggestion;
import com.mannor.mealscoming.service.CustomerComplaintSuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customerComplaintSuggestion")
public class CustomerComplaintSuggestionController {

    @Autowired
    private CustomerComplaintSuggestionService customerComplaintSuggestionService;

    /**
     * 新增客户投诉建议信息
     * @param customerComplaintSuggestion 客户投诉建议实体
     * @return 新增结果
     */
    @PostMapping
    public boolean save(@RequestBody CustomerComplaintSuggestion customerComplaintSuggestion) {
        return customerComplaintSuggestionService.save(customerComplaintSuggestion);
    }

    /**
     * 删除客户投诉建议信息
     * @param id 客户投诉建议信息的主键
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return customerComplaintSuggestionService.removeById(id);
    }

    /**
     * 修改客户投诉建议信息
     * @param customerComplaintSuggestion 客户投诉建议实体
     * @return 修改结果
     */
    @PutMapping
    public boolean update(@RequestBody CustomerComplaintSuggestion customerComplaintSuggestion) {
        return customerComplaintSuggestionService.updateById(customerComplaintSuggestion);
    }

    /**
     * 根据主键查询单个客户投诉建议信息
     * @param id 客户投诉建议信息的主键
     * @return 客户投诉建议信息实体
     */
    @GetMapping("/{id}")
    public CustomerComplaintSuggestion getById(@PathVariable Long id) {
        return customerComplaintSuggestionService.getById(id);
    }

    /**
     * 查询所有客户投诉建议信息
     * @return 客户投诉建议信息列表
     */
    @GetMapping
    public List<CustomerComplaintSuggestion> findAll() {
        return customerComplaintSuggestionService.list();
    }

    /**
     * 分页查询客户投诉建议信息
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return 分页后的客户投诉建议信息
     */
    @GetMapping("/page")
    public Page<CustomerComplaintSuggestion> findPage(@RequestParam Integer pageNum,
                                                      @RequestParam Integer pageSize) {
        QueryWrapper<CustomerComplaintSuggestion> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return customerComplaintSuggestionService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }
}