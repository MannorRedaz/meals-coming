package com.mannor.mealscoming.dto;

import com.mannor.mealscoming.entity.Merchant;
import com.mannor.mealscoming.entity.MerchantDetails;
import lombok.Data;

@Data
public class MerchantDto {
    private Merchant merchant;
    private MerchantDetails merchantDetails;
}
