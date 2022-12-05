package com.robosoft.lorem.model;

import lombok.Data;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Data
public class OrderInfo {

    private int cartId;
    private List<Card> cardList;
    private String addressDesc;
    private Date scheduleDate;
    private Time scheduleTime;
    private int creditScore;
    private RestaurantInfo restaurantInfo;
    private Double averageDeliveryTime;

}
