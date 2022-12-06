package com.robosoft.lorem.model;

import lombok.Data;

import java.util.List;
@Data
public class RestaurantDetails {
    private int restaurantId;
    private String restaurantName;
    private Integer overAllRating;
    private double minimumCost;
    private String restaurantType;
    private String profilePicLink;
    private long duration;
    private String workingHours;
    List<Integer> deliveryRating;
}
