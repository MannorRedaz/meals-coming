package com.mannor.mealscoming.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mannor.mealscoming.common.R;
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
     *
     * @param complaintSuggestionManagement 投诉建议管理实体
     * @return 新增结果
     */
    @PostMapping
    public R<Boolean> save(@RequestBody ComplaintSuggestionManagement complaintSuggestionManagement) {
        return R.success(complaintSuggestionManagementService.save(complaintSuggestionManagement));
    }

    /**
     * 删除投诉建议管理信息
     * @param id 投诉建议管理信息的主键
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public R<Boolean> delete(@PathVariable Long id) {
        return R.success(complaintSuggestionManagementService.removeById(id));
    }

    /**
     * 修改投诉建议管理信息
     * @param complaintSuggestionManagement 投诉建议管理实体
     * @return 修改结果
     */
    @PutMapping
    public R<Boolean> update(@RequestBody ComplaintSuggestionManagement complaintSuggestionManagement) {
        return R.success(complaintSuggestionManagementService.updateById(complaintSuggestionManagement));
    }

    /**
     * 根据主键查询单个投诉建议管理信息
     * @param id 投诉建议管理信息的主键
     * @return 投诉建议管理信息实体
     */
    @GetMapping("/{id}")
    public R<ComplaintSuggestionManagement> getById(@PathVariable Long id) {
        return R.success(complaintSuggestionManagementService.getById(id));
    }

    /**
     * 查询所有投诉建议管理信息
     * @return 投诉建议管理信息列表
     */
    @GetMapping
    public R<List<ComplaintSuggestionManagement>> findAll() {
        return R.success(complaintSuggestionManagementService.list());
    }

    /**
     * 分页查询投诉建议管理信息
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return 分页后的投诉建议管理信息
     */
    @GetMapping("/page")
    public R<Page<ComplaintSuggestionManagement>> findPage(@RequestParam Integer pageNum,
                                                           @RequestParam Integer pageSize) {
        QueryWrapper<ComplaintSuggestionManagement> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        Page<ComplaintSuggestionManagement> pageResult = complaintSuggestionManagementService.page(new Page<>(pageNum, pageSize), queryWrapper);
        return R.success(pageResult);
    }
}