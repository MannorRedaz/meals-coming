package com.mannor.mealscoming.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mannor.mealscoming.entity.EvaluationManagement;
import com.mannor.mealscoming.service.EvaluationManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/evaluationManagement")
public class EvaluationManagementController {

    @Autowired
    private EvaluationManagementService evaluationManagementService;

    /**
     * 新增评价管理信息
     * @param evaluationManagement 评价管理实体
     * @return 新增结果
     */
    @PostMapping
    public boolean save(@RequestBody EvaluationManagement evaluationManagement) {
        return evaluationManagementService.save(evaluationManagement);
    }

    /**
     * 删除评价管理信息
     * @param id 评价管理信息的主键
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return evaluationManagementService.removeById(id);
    }

    /**
     * 修改评价管理信息
     * @param evaluationManagement 评价管理实体
     * @return 修改结果
     */
    @PutMapping
    public boolean update(@RequestBody EvaluationManagement evaluationManagement) {
        return evaluationManagementService.updateById(evaluationManagement);
    }

    /**
     * 根据主键查询单个评价管理信息
     * @param id 评价管理信息的主键
     * @return 评价管理信息实体
     */
    @GetMapping("/{id}")
    public EvaluationManagement getById(@PathVariable Long id) {
        return evaluationManagementService.getById(id);
    }

    /**
     * 查询所有评价管理信息
     * @return 评价管理信息列表
     */
    @GetMapping
    public List<EvaluationManagement> findAll() {
        return evaluationManagementService.list();
    }

    /**
     * 分页查询评价管理信息
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return 分页后的评价管理信息
     */
    @GetMapping("/page")
    public Page<EvaluationManagement> findPage(@RequestParam Integer pageNum,
                                               @RequestParam Integer pageSize) {
        QueryWrapper<EvaluationManagement> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return evaluationManagementService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }
}