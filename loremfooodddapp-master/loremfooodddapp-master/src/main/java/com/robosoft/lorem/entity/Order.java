package com.robosoft.lorem.entity;


import lombok.Data;

@Data
public class Order {

    private Integer orderId;
    private String orderStatus;
    private String orderType;
    private Integer userId;
    private String contactName;
    private String contactNo;
    private Integer addressId;
    private Integer cartId;
    private Integer restaurantId;
}
