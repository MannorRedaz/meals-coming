package com.mannor.mealscoming.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mannor.mealscoming.common.CustomException;
import com.mannor.mealscoming.dto.DishDto;
import com.mannor.mealscoming.entity.*;
import com.mannor.mealscoming.mapper.DishMapper;
import com.mannor.mealscoming.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {


    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private SetmealDishService setmealDishService;


    /**
     * 批量删除菜品
     *
     * @param ids
     */
    @Transactional
    @Override
    public void removeByIdsWithFlavor(Long ids[]) {
        //查询当前状态，看是否是可以删除的
        LambdaQueryWrapper<Dish> qw = new LambdaQueryWrapper<>();
        qw.eq(Dish::getStatus, 1).in(Dish::getId, ids);
        int count = this.count(qw);

        if (count > 0) {
            //不能删除就抛出一个业务异常
            throw new CustomException("正在售卖中，不可以删除！");
        }


        for (Long dishId : ids) {
            this.removeById(dishId);//删除setmeal表中数据
            LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DishFlavor::getDishId, dishId);
            List<DishFlavor> dishFlavorsList = dishFlavorService.list(queryWrapper);
            dishFlavorsList.stream().map(item -> {
                Long id = item.getId();
                dishFlavorService.removeById(id);//删除dishFlavorService表中数据
                return item;
            });
        }


    }

    /**
     * 新增菜品，并保存口味数据
     *
     * @param dishDto
     */
    @Transactional  //由于涉及到多张表的处理，所以在此采用事务管理，并且还需要再启动类上添加@EnableTransactionManagement才能生效
    @Override
    public void saveWithFlavor(DishDto dishDto) {
        this.save(dishDto);//因为继承自父类DIsh，所以dish数据可以直接通过DishDto传入
        Long DishId = dishDto.getId();//菜品id

        List<DishFlavor> flavors = dishDto.getFlavors();//菜品口味
        flavors = flavors.stream().map(item -> {
            item.setDishId(DishId);
            return item;
        }).collect(Collectors.toList());//设置每一个list的DishId并且把flavor对象又转回list
        dishFlavorService.saveBatch(flavors);


    }

    /**
     * 根据id查询菜品表和口味表
     *
     * @param id
     */
    @Transactional
    @Override
    public DishDto getBtIdWithFlavor(Long id) {
        DishDto dishDto = new DishDto();

        //在dish表中查询
        Dish dish = this.getById(id);
        BeanUtils.copyProperties(dish, dishDto);

        //在dishFlavor表中查询
        LambdaQueryWrapper<DishFlavor> qw = new LambdaQueryWrapper<>();
        qw.eq(DishFlavor::getDishId, id);
        List<DishFlavor> list = dishFlavorService.list(qw);//根据id查询flavor表中数据
        dishDto.setFlavors(list);//赋给DishDto对象

        return dishDto;
    }

    /**
     * 菜品信息更新操作
     *
     * @param dishDto
     */
    @Transactional
    @Override
    public void updateWithFlavor(DishDto dishDto) {
        //更新菜品dish的数据
        this.updateById(dishDto);

        //清理口味表的数据delete操作
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorService.remove(queryWrapper);

        //更新口味表
        List<DishFlavor> flavors = dishDto.getFlavors();

        flavors = flavors.stream().map(item -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());//设置每一个list的DishId并且把flavor对象又转回list
        dishFlavorService.saveBatch(flavors);

        //更新购物车中的数据（假如存在）
        Long productId = dishDto.getId();
        try {
            BigDecimal latestPrice = this.getById(productId).getPrice();
            // 更新购物车表中对应商品的价格
            shoppingCartService.updateCartItemPrice(1, productId, latestPrice);
        } catch (NullPointerException e) {
            e.printStackTrace();
            log.info("发生空指针异常");
        }
        //更新套餐中的数据（假如存在）
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SetmealDish::getDishId,productId);
        List<SetmealDish> list = setmealDishService.list(lambdaQueryWrapper);
         list.forEach(item->{
             LambdaQueryWrapper<SetmealDish> lam = new LambdaQueryWrapper<>();
             lam.eq(SetmealDish::getDishId,productId);
             SetmealDish setmealDish = new SetmealDish();
             setmealDish.setPrice(this.getById(productId).getPrice());
             setmealDishService.update(setmealDish,lam);
         });

    }
}
