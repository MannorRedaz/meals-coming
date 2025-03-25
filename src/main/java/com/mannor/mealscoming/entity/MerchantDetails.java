package com.mannor.mealscoming.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class MerchantDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Long merchantId;
    private String accountName;
    private String name;
    private String phone;
    private String gender;
    private String idCard;
    private String businessLicense;
    private String address;

    public MerchantDetails(String accountName, String name, String phone, String gender, String idCard, String businessLicense, String address) {
        this.accountName = accountName;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.idCard = idCard;
        this.businessLicense = businessLicense;
        this.address = address;
    }

}
