package com.robosoft.lorem.model;


import lombok.Data;

@Data
public class OrderModel {

    private Integer orderId;
    private String restaurantName;
    private String orderStatus;
    private String restaurantAddress;
    private Integer itemsCount;
    private double amount;


}
