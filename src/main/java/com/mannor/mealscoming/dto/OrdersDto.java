package com.mannor.mealscoming.dto;

import com.mannor.mealscoming.entity.OrderDetail;
import com.mannor.mealscoming.entity.Orders;
import lombok.Data;
import java.util.List;

@Data
public class OrdersDto extends Orders {

    private String userName;

    private String phone;

    private String address;

    private String consignee;

    private String sumNum;

    private List<OrderDetail> orderDetails;
	
}
