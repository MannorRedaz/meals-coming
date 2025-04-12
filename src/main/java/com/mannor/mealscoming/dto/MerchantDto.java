package com.mannor.mealscoming.dto;

import com.mannor.mealscoming.entity.Merchant;
import com.mannor.mealscoming.entity.MerchantAudit;
import com.mannor.mealscoming.entity.MerchantDetails;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class MerchantDto extends Merchant {
    private MerchantDetails merchantDetails;
    private MerchantAudit merchantAudit;
}
