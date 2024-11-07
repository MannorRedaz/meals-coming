package com.mannor.mealscoming.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mannor.mealscoming.common.R;
import com.mannor.mealscoming.entity.ShoppingCart;

import java.math.BigDecimal;


public interface ShoppingCartService extends IService<ShoppingCart> {
    R<ShoppingCart> subDishOrSetmeal(ShoppingCart shoppingCart);

    void clean();

    void updateCartItemPrice(int i, long productId, BigDecimal latestPrice);
}
