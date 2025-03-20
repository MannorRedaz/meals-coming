package com.mannor.mealscoming.vo;

import com.mannor.mealscoming.entity.Merchant;
import com.mannor.mealscoming.entity.MerchantAudit;
import com.mannor.mealscoming.entity.MerchantDetails;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MerchantVo extends Merchant {

    private MerchantAudit merchantAudit;

    private MerchantDetails merchantDetails;

}
