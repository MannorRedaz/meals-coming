package com.mannor.reggie_take_out.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mannor.reggie_take_out.common.R;
import com.mannor.reggie_take_out.entity.ShoppingCart;

import java.math.BigDecimal;


public interface ShoppingCartService extends IService<ShoppingCart> {
    R<ShoppingCart> subDishOrSetmeal(ShoppingCart shoppingCart);

    void clean();

    void updateCartItemPrice(int i, long productId, BigDecimal latestPrice);
}
