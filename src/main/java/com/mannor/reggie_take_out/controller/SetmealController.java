package com.mannor.reggie_take_out.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mannor.reggie_take_out.common.R;
import com.mannor.reggie_take_out.dto.SetmealDto;
import com.mannor.reggie_take_out.entity.Category;
import com.mannor.reggie_take_out.entity.Setmeal;
import com.mannor.reggie_take_out.service.CategoryService;
import com.mannor.reggie_take_out.service.SetmealDishService;
import com.mannor.reggie_take_out.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 信息分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(Integer page, Integer pageSize, String name) {
        log.info("套餐管理分页查询的参数：page:{},pageSize:{},name:{}", page, pageSize, name);
        //构造分页构造器
        Page<Setmeal> pageInfo = new Page(page, pageSize);
        Page<SetmealDto> dtoPage = new Page<>();

        //条件限定
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name), Setmeal::getName, name);//套餐管理的分页查询
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);//排序

        //执行
        setmealService.page(pageInfo, queryWrapper);

        //套餐分类渲染给前端
        BeanUtils.copyProperties(pageInfo, dtoPage, "records");//第三个参数是Page对象中的属性,由于泛型不同，所以我们需要自己设置该属性
        List<Setmeal> records = pageInfo.getRecords();

        List<SetmealDto> list = records.stream().map(item -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);

            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;

        }).collect(Collectors.toList());
        dtoPage.setRecords(list);

        return R.success(dtoPage);
    }

    /**
     * 添加套餐
     *
     * @param setmealDto
     * @return
     */
    @CacheEvict(value = "setmealCache", allEntries = true)//参数：allEntries，表示删除setmealCache下的所有缓存数据
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        log.info("添加套餐，参数：setmealDto={}", setmealDto);
        setmealService.saveWithDish(setmealDto);

        return R.success("套餐添加成功!");
    }

    /**
     * 根据id查询套餐，用于更新的页面回显
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<SetmealDto> getById(@PathVariable Long id) {
        log.info("更新的参数：id={}", id);
        SetmealDto setmealDto = setmealService.getByIdWithDish(id);
        return R.success(setmealDto);
    }

    /**
     * 批量删除套餐
     *
     * @param ids
     * @return
     */
    @CacheEvict(value = "setmealCache", allEntries = true)//参数：allEntries，表示删除setmealCache下的所有缓存数据
    @DeleteMapping
    public R<String> deleteById(Long ids[]) {
        log.info("套餐删除的参数：setmealDto={}", ids);
        setmealService.removeByIdsWithDish(ids);
        return R.success("套餐删除成功");
    }


    /**
     * 套餐售卖状态更改
     *
     * @param status
     * @param ids
     * @return
     */
    @CacheEvict(value = "setmealCache", allEntries = true)//参数：allEntries，表示删除setmealCache下的所有缓存数据
    @PostMapping("/status/{status}")
    public R<String> status(@PathVariable Integer status, Long ids[]) {
        log.info("套餐售卖状态更改参数：status={}，ids={}", status, ids);
        for (Long id : ids) {
            Setmeal setmeal = new Setmeal();
            setmeal.setStatus(status);
            setmeal.setId(id);
            setmealService.updateById(setmeal);
        }

        return R.success("状态修改成功");
    }

    /**
     * 套餐列表查询
     *
     * @param setmeal
     * @return
     */
    @Cacheable(value = "setmealCache", key = "#setmeal.categoryId + '_' + #setmeal.status")
    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal) {
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(setmeal.getCategoryId() != null, Setmeal::getCategoryId, setmeal.getCategoryId());
        lambdaQueryWrapper.eq(setmeal.getStatus() != null, Setmeal::getStatus, setmeal.getStatus());
        lambdaQueryWrapper.orderByDesc(Setmeal::getUpdateTime);
        List<Setmeal> list = setmealService.list(lambdaQueryWrapper);
        SetmealDto setmealDto = null;
        list.stream().map(item -> {
            Long id = item.getCategoryId();

            return item;
        }).collect(Collectors.toList());

        return R.success(list);
    }


    /**
     * 更新套餐数据源
     *
     * @param setmealDto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto) {
        log.info("更新套餐数据的参数：{}", setmealDto);
        setmealService.updateWithSetmealDish(setmealDto);

        return R.success("更新成功！");
    }


}
