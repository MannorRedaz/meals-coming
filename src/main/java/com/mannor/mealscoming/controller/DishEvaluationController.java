package com.mannor.mealscoming.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mannor.mealscoming.entity.DishEvaluation;
import com.mannor.mealscoming.service.DishEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dishEvaluation")
public class DishEvaluationController {

    @Autowired
    private DishEvaluationService dishEvaluationService;

    /**
     * 新增菜品评价信息
     * @param dishEvaluation 菜品评价实体
     * @return 新增结果
     */
    @PostMapping
    public boolean save(@RequestBody DishEvaluation dishEvaluation) {
        return dishEvaluationService.save(dishEvaluation);
    }

    /**
     * 删除菜品评价信息
     * @param id 菜品评价信息的主键
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return dishEvaluationService.removeById(id);
    }

    /**
     * 修改菜品评价信息
     * @param dishEvaluation 菜品评价实体
     * @return 修改结果
     */
    @PutMapping
    public boolean update(@RequestBody DishEvaluation dishEvaluation) {
        return dishEvaluationService.updateById(dishEvaluation);
    }

    /**
     * 根据主键查询单个菜品评价信息
     * @param id 菜品评价信息的主键
     * @return 菜品评价信息实体
     */
    @GetMapping("/{id}")
    public DishEvaluation getById(@PathVariable Long id) {
        return dishEvaluationService.getById(id);
    }

    /**
     * 查询所有菜品评价信息
     * @return 菜品评价信息列表
     */
    @GetMapping
    public List<DishEvaluation> findAll() {
        return dishEvaluationService.list();
    }

    /**
     * 分页查询菜品评价信息
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return 分页后的菜品评价信息
     */
    @GetMapping("/page")
    public Page<DishEvaluation> findPage(@RequestParam Integer pageNum,
                                         @RequestParam Integer pageSize) {
        QueryWrapper<DishEvaluation> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return dishEvaluationService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }
}