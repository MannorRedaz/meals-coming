package com.mannor.mealscoming.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mannor.mealscoming.entity.ComplaintSuggestionManagement;
import com.mannor.mealscoming.service.ComplaintSuggestionManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/complaintSuggestionManagement")
public class ComplaintSuggestionManagementController {

    @Autowired
    private ComplaintSuggestionManagementService complaintSuggestionManagementService;

    /**
     * 新增投诉建议管理信息
     * @param complaintSuggestionManagement 投诉建议管理实体
     * @return 新增结果
     */
    @PostMapping
    public boolean save(@RequestBody ComplaintSuggestionManagement complaintSuggestionManagement) {
        return complaintSuggestionManagementService.save(complaintSuggestionManagement);
    }

    /**
     * 删除投诉建议管理信息
     * @param id 投诉建议管理信息的主键
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return complaintSuggestionManagementService.removeById(id);
    }

    /**
     * 修改投诉建议管理信息
     * @param complaintSuggestionManagement 投诉建议管理实体
     * @return 修改结果
     */
    @PutMapping
    public boolean update(@RequestBody ComplaintSuggestionManagement complaintSuggestionManagement) {
        return complaintSuggestionManagementService.updateById(complaintSuggestionManagement);
    }

    /**
     * 根据主键查询单个投诉建议管理信息
     * @param id 投诉建议管理信息的主键
     * @return 投诉建议管理信息实体
     */
    @GetMapping("/{id}")
    public ComplaintSuggestionManagement getById(@PathVariable Long id) {
        return complaintSuggestionManagementService.getById(id);
    }

    /**
     * 查询所有投诉建议管理信息
     * @return 投诉建议管理信息列表
     */
    @GetMapping
    public List<ComplaintSuggestionManagement> findAll() {
        return complaintSuggestionManagementService.list();
    }

    /**
     * 分页查询投诉建议管理信息
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return 分页后的投诉建议管理信息
     */
    @GetMapping("/page")
    public Page<ComplaintSuggestionManagement> findPage(@RequestParam Integer pageNum,
                                                        @RequestParam Integer pageSize) {
        QueryWrapper<ComplaintSuggestionManagement> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return complaintSuggestionManagementService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }
}