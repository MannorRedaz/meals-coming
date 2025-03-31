package com.mannor.mealscoming.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mannor.mealscoming.common.R;
import com.mannor.mealscoming.dto.DishDto;
import com.mannor.mealscoming.entity.*;
import com.mannor.mealscoming.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private MerchantService merchantService;


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
        log.info("菜品分页查询的参数：page:{},pageSize:{},name:{}", page, pageSize, name);
        //构造分页构造器
        Page<Dish> pageInfo = new Page(page, pageSize);
        Page<DishDto> dishDtoInfo = new Page<>();
        //条件限定
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name), Dish::getName, name);//名称的模糊查询
        queryWrapper.orderByDesc(Dish::getUpdateTime);//排序
        dishService.page(pageInfo, queryWrapper);//执行分页查询

        //将pageInfo对象的拷贝到dishDtoInfo
        BeanUtils.copyProperties(pageInfo, dishDtoInfo, "records");//第三个参数是Page对象中忽略的属性，我们需要自己设置该属性

        List<Dish> records = pageInfo.getRecords(); //此处records是我们自定义设置之后的属性
        List<DishDto> list = records.stream().map(item -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);//拷贝对象

            Long categoryId = item.getCategoryId();//获取分类id，进而获取分类名
            Category category = categoryService.getById(categoryId);

            if (category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);//赋值到dishDto中
            }

            return dishDto;
        }).collect(Collectors.toList());//最后转换成list集合

        dishDtoInfo.setRecords(list);


        return R.success(dishDtoInfo);
    }

    /**
     * 添加菜品
     *
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        log.info("添加菜品的参数dishDto={}", dishDto);
        dishService.saveWithFlavor(dishDto);

        //更新之后清理菜品数据，免得产生脏数据
        //Set keys = redisTemplate.keys("dish_*");
        //redisTemplate.delete(keys);

        //精确清理-->清理某个分类下的菜品缓存数据
        String key = "dish_" + dishDto.getCategoryId() + "_1";
        redisTemplate.delete(key);
        return R.success("菜品添加成功");
    }

    /**
     * 根据id查询并返回菜品信息，菜品修改页面回显
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> getById(@PathVariable Long id) {
        log.info("根据id查询菜品信息：id={}", id);
        DishDto dishDto = dishService.getBtIdWithFlavor(id);
        return R.success(dishDto);
    }

    /**
     * 批量删除菜品
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> deleteById(Long ids[]) {
        log.info("菜品删除的参数：setmealDto={}", ids);
        dishService.removeByIdsWithFlavor(ids);
        return R.success("菜品删除成功");
    }


    /**
     * 添加菜品
     *
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {
        log.info("更新菜品的参数dishDto={}", dishDto);
        dishService.updateWithFlavor(dishDto);

        //更新之后清理菜品数据，免得产生脏数据
        //Set keys = redisTemplate.keys("dish_*");
        //redisTemplate.delete(keys);

        //精确清理-->清理某个分类下的菜品缓存数据
        String key = "dish_" + dishDto.getCategoryId() + "_1";
        redisTemplate.delete(key);
        return R.success("菜品更新成功");
    }

    /**
     * 套餐管理中添加套餐中的菜品
     *
     * @param dish
     * @return
     */
    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish) {
        log.info("/dish/list查询参数：{}" + dish);
        List<DishDto> dishDtoList = null;
        //动态的构造一个key，用于redis缓存
        String key = "dish_" + dish.getCategoryId() + "_" + dish.getStatus();
        //从redis获取缓存的数据
        dishDtoList = (List<DishDto>) redisTemplate.opsForValue().get(key);

        if (dishDtoList != null) {
            //如果存在，直接返回，不需要查询数据库
//            return R.success(dishDtoList);
        }
        //构造查询条件
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
        // 添加商家条件
        if (dish.getMerchantId() != null) {
            queryWrapper.eq(Dish::getMerchantId, dish.getMerchantId());
        } else {
            Merchant mer = merchantService.getOne(new LambdaQueryWrapper<Merchant>().last("LIMIT 1"));
            queryWrapper.eq(Dish::getMerchantId, mer.getId());
        }

        //添加条件，查询状态为1（起售状态）的菜品
        queryWrapper.eq(Dish::getStatus, 1);

        //添加排序条件
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> list = dishService.list(queryWrapper);

        dishDtoList = list.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long categoryId = item.getCategoryId();//分类id
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            //当前菜品的id
            Long dishId = item.getId();
            LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(DishFlavor::getDishId, dishId);
            //SQL:select * from dish_flavor where dish_id = ?
            List<DishFlavor> dishFlavorList = dishFlavorService.list(lambdaQueryWrapper);
            dishDto.setFlavors(dishFlavorList);
            LambdaQueryWrapper<OrderDetail> saleQueryWrapper = new LambdaQueryWrapper<>();
            saleQueryWrapper.eq(OrderDetail::getDishId, dishId);
            dishDto.setSaleNum(orderDetailService.count(saleQueryWrapper));//查询月销
            return dishDto;
        }).collect(Collectors.toList());

        //不存在，就需要查询数据库，将查询到的数据缓存到redis中华
        redisTemplate.opsForValue().set(key, dishDtoList, 60, TimeUnit.MINUTES);//设置60分钟后过期

        return R.success(dishDtoList);
    }


    /**
     * 菜品售卖状态更改
     *
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public R<String> status(@PathVariable Integer status, Long ids[]) {
        log.info("菜品售卖状态更改参数：status={}，ids={}", status, ids);
        for (Long id : ids) {
            Dish dish = new Dish();
            dish.setStatus(status);
            dish.setId(id);
            dishService.updateById(dish);
        }

        //更新之后清理菜品数据，免得产生脏数据
        Set keys = redisTemplate.keys("dish_*");
        redisTemplate.delete(keys);
        return R.success("状态修改成功");
    }


}
