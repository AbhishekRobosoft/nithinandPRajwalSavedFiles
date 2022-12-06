package com.robosoft.lorem.entity;


import lombok.Data;

@Data
public class Payment {

    private Integer paymentId;
    private Integer userId;
    private Integer orderId;
    private Double amount;
    private String promoCode;
    private String cardNumber;
}
