package com.mannor.reggie_take_out.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.DatabindContext;
import com.mannor.reggie_take_out.common.BaseContext;
import com.mannor.reggie_take_out.common.R;
import com.mannor.reggie_take_out.entity.ShoppingCart;
import com.mannor.reggie_take_out.mapper.ShoppingCartMapper;
import com.mannor.reggie_take_out.service.ShoppingCartService;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    // @Override
    public R<ShoppingCart> addDishOrSetmeal(ShoppingCart shoppingCart) {
        //设置用户id，指定当前是哪个用户的购物车数据
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);
        Long dishId = shoppingCart.getDishId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, currentId);
        if (dishId != null) {
            //添加到购物车的是菜品
            queryWrapper.eq(ShoppingCart::getDishId, dishId);
        } else {
            //添加到购物车的是套餐
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }

        //查询当前菜品或者套餐是否在购物车中
        //SQL:select * from shopping_cart where user_id = ? and dish_id/setmeal_id = ?
        ShoppingCart cartServiceOne = shoppingCartService.getOne(queryWrapper);

        if (cartServiceOne != null) {
            //如果已经存在，就在原来数量基础上加一
            Integer number = cartServiceOne.getNumber();
            cartServiceOne.setNumber(number + 1);
            shoppingCartService.updateById(cartServiceOne);
        } else {
            //如果不存在，则添加到购物车，数量默认就是一
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            cartServiceOne = shoppingCart;
        }


        return R.success(cartServiceOne);
    }

    @Override
    public R<ShoppingCart> subDishOrSetmeal(ShoppingCart shoppingCart) {
        Long dishId = shoppingCart.getDishId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();

        //代表数量减少的是菜品数量
        if (dishId != null) {
            // 通过dishId查出购物车对象
            queryWrapper.eq(ShoppingCart::getDishId, dishId);
            // 这里必须要加两个条件，否则会出现用户互相修改对方与自己购物车中相同套餐或者是菜品的数量
            queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
            ShoppingCart cart1 = shoppingCartMapper.selectOne(queryWrapper);
            cart1.setNumber(cart1.getNumber() - 1);
            Integer LatestNumber = cart1.getNumber();
            if (LatestNumber > 0) {
                //对数据进行更新操作
                shoppingCartMapper.updateById(cart1);
            } else if (LatestNumber == 0) {
                //如果购物车的菜品数量减为0，那么就把菜品从购物车删除
                shoppingCartMapper.deleteById(cart1.getId());
            } else if (LatestNumber < 0) {
                return R.error("操作异常");
            }

            return R.success(cart1);
        }

        Long setmealId = shoppingCart.getSetmealId();
        if (setmealId != null) {
            // 代表是套餐数量减少
            queryWrapper.eq(ShoppingCart::getSetmealId, setmealId).eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
            ShoppingCart cart2 = shoppingCartMapper.selectOne(queryWrapper);
            cart2.setNumber(cart2.getNumber() - 1);
            Integer LatestNumber = cart2.getNumber();
            if (LatestNumber > 0) {
                //对数据进行更新操作
                shoppingCartMapper.updateById(cart2);
            } else if (LatestNumber == 0) {
                //如果购物车的套餐数量减为0，那么就把套餐从购物车删除
                shoppingCartMapper.deleteById(cart2.getId());
            } else if (LatestNumber < 0) {
                return R.error("操作异常");
            }
            return R.success(cart2);
        }
        //如果两个大if判断都进不去
        return R.error("操作异常");
    }

    @Override
    public void clean() {
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        shoppingCartMapper.delete(queryWrapper);

    }

    /**
     * 假如价格数据发生了变化，则购物车中的数据金额也会发生变化
     *
     * @param id
     * @param productId
     * @param latestPrice
     */
    @Override
    public void updateCartItemPrice(int id, long productId, BigDecimal latestPrice) {
        LambdaQueryWrapper<ShoppingCart> lam = new LambdaQueryWrapper<>();
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setAmount(latestPrice.divide(new BigDecimal("100")));
        if (id == 1) {
            lam.eq(ShoppingCart::getDishId, productId);
            this.update(shoppingCart, lam);
        } else if (id == 2) {
            lam.eq(ShoppingCart::getSetmealId, productId);
            this.update(shoppingCart, lam);
        }
    }

}
