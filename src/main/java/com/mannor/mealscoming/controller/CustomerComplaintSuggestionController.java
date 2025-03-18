package com.mannor.mealscoming.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mannor.mealscoming.entity.OrderComplaint;
import com.mannor.mealscoming.service.OrderComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customerComplaintSuggestion")
public class CustomerComplaintSuggestionController {

    @Autowired
    private OrderComplaintService orderComplaintService;

    /**
     * 新增客户投诉建议信息
     * @param orderComplaint 客户投诉建议实体
     * @return 新增结果
     */
    @PostMapping
    public boolean save(@RequestBody OrderComplaint orderComplaint) {
        return orderComplaintService.save(orderComplaint);
    }

    /**
     * 删除客户投诉建议信息
     * @param id 客户投诉建议信息的主键
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return orderComplaintService.removeById(id);
    }

    /**
     * 修改客户投诉建议信息
     * @param orderComplaint 客户投诉建议实体
     * @return 修改结果
     */
    @PutMapping
    public boolean update(@RequestBody OrderComplaint orderComplaint) {
        return orderComplaintService.updateById(orderComplaint);
    }

    /**
     * 根据主键查询单个客户投诉建议信息
     * @param id 客户投诉建议信息的主键
     * @return 客户投诉建议信息实体
     */
    @GetMapping("/{id}")
    public OrderComplaint getById(@PathVariable Long id) {
        return orderComplaintService.getById(id);
    }

    /**
     * 查询所有客户投诉建议信息
     * @return 客户投诉建议信息列表
     */
    @GetMapping
    public List<OrderComplaint> findAll() {
        return orderComplaintService.list();
    }

    /**
     * 分页查询客户投诉建议信息
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return 分页后的客户投诉建议信息
     */
    @GetMapping("/page")
    public Page<OrderComplaint> findPage(@RequestParam Integer pageNum,
                                         @RequestParam Integer pageSize) {
        QueryWrapper<OrderComplaint> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return orderComplaintService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(IllegalArgumentException e) {
        return e.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeneralException(Exception e) {
        return "服务器内部错误: " + e.getMessage();
    }
}