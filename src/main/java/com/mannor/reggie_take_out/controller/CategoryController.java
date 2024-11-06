package com.mannor.reggie_take_out.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mannor.reggie_take_out.common.R;
import com.mannor.reggie_take_out.entity.Category;
import com.mannor.reggie_take_out.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类管理
 */
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     *
     * @param category
     * @return
     */
    @PostMapping()
    public R<String> save(@RequestBody Category category) {
        log.info("新增分类参数：{}", category);
        categoryService.save(category);
        return R.success("添加分类成功！");
    }

    /**
     * 分页查询
     *
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(Integer page, Integer pageSize) {
        log.info("分类管理分页查询，参数：page={}，pageSize={}", page, pageSize);
        Page pageInfo = new Page(page, pageSize);

        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        lqw.orderByAsc(Category::getSort);
        categoryService.page(pageInfo, lqw);
        return R.success(pageInfo);
    }


    /**
     * 更新分类
     *
     * @param category
     * @return
     */
    @PutMapping()
    public R<String> update(@RequestBody Category category) {
        log.info("更新分类参数：{}", category);
        categoryService.updateById(category);
        return R.success("修改分类成功！");
    }

    /**
     * 根据id删除分类
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long ids) {
        log.info("删除分类参数：id={}", ids);
        categoryService.remove(ids); //在service中自定义的方法
        return R.success("分类删除成功");
    }

    /**
     * 添加菜品时的查询
     *
     * @param category
     * @return
     */
    @GetMapping("/list")
    public R<List<Category>> list(Category category) {
        log.info("添加菜品时查询的参数：type={}", category);
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        lqw.eq(category.getType() != null, Category::getType, category.getType());//条件
        lqw.orderByAsc(Category::getSort).orderByAsc(Category::getUpdateTime);//排序条件
        List<Category> list = categoryService.list(lqw);


        return R.success(list);
    }

}
