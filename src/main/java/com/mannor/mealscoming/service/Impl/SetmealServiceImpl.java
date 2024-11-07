package com.mannor.mealscoming.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mannor.mealscoming.common.CustomException;
import com.mannor.mealscoming.dto.SetmealDto;
import com.mannor.mealscoming.entity.Setmeal;
import com.mannor.mealscoming.entity.SetmealDish;
import com.mannor.mealscoming.mapper.SetmealMapper;
import com.mannor.mealscoming.service.SetmealDishService;
import com.mannor.mealscoming.service.SetmealService;
import com.mannor.mealscoming.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加套餐
     *
     * @param setmealDto
     */
    @Transactional
    @Override
    public void saveWithDish(SetmealDto setmealDto) {
        //添加数据到setmeal表
        this.save(setmealDto);

        //添加数据到setmealDish表
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes = setmealDishes.stream().map(item -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(setmealDishes);
    }

    /**
     * 修改套餐时的页面回显
     *
     * @param id
     * @return
     */
    @Override
    public SetmealDto getByIdWithDish(Long id) {
        SetmealDto setmealDto = new SetmealDto();
        //对setmeal表的操作
        Setmeal setmeal = this.getById(id);
        BeanUtils.copyProperties(setmeal, setmealDto);

        //对setmealDish表的操作
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId, id);
        List<SetmealDish> list = setmealDishService.list(queryWrapper);

        setmealDto.setSetmealDishes(list);

        return setmealDto;
    }

    /**
     * 批量删除套餐
     *
     * @param ids
     */
    @Transactional
    @Override
    public void removeByIdsWithDish(Long ids[]) {
        //查询当前状态，看是否是可以删除的
        LambdaQueryWrapper<Setmeal> qw = new LambdaQueryWrapper<>();
        qw.eq(Setmeal::getStatus, 1).in(Setmeal::getId, ids);
        int count = this.setmealService.count(qw);
        if (count > 0) {
            //不能删除就抛出一个业务异常
            throw new CustomException("正在售卖中，不可以删除！");
        }

        //对表中数据进行删除
        List<Long> idList = Arrays.stream(ids).collect(Collectors.toList());
        this.removeByIds(idList);//对setmeal表的操作
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId, ids);
        setmealDishService.remove(lambdaQueryWrapper);//删除setmealDish表的数据

      /*  //下面的代码较赘余，所以改进后如上
        for (Long setmealId : ids) {
            setmealService.removeById(setmealId);//删除setmeal表中数据
            LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SetmealDish::getSetmealId, setmealId);
            List<SetmealDish> setmealDishList = setmealDishService.list(queryWrapper);
            setmealDishList.stream().map(item -> {
                Long id = item.getId();
                setmealDishService.removeById(id);//删除setmealDish表中数据
                return item;
            });
        }*/


    }


    @Override
    public void updateWithSetmealDish(SetmealDto setmealDto) {
        //更新套餐Setmealh的数据
        this.updateById(setmealDto);

        //清理原有套餐中的数据delete操作
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId, setmealDto.getId());
        setmealDishService.remove(queryWrapper);

        //更新口味表
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes = setmealDishes.stream().map(item -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());//设置每一个list的SetmealId并且把Dish对象又转回list
        setmealDishService.saveBatch(setmealDishes);


        //更新购物车中的数据（假如存在）
        Long productId = setmealDto.getId();
        synchronized (new Object()) {
            try {
                BigDecimal latestPrice = this.getById(productId).getPrice();
                // 更新购物车表中对应商品的价格
                shoppingCartService.updateCartItemPrice(2, productId, latestPrice);
            } catch (NullPointerException e) {
                e.printStackTrace();
                log.info("发生空指针异常");
            }
        }
    }
}
