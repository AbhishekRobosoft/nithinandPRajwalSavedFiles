package com.robosoft.lorem.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.robosoft.lorem.routeResponse.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class RestaurantSearchModel {

    private Integer restaurantId;
    private String restaurantName;
    private int userId;
    private Double overAllRating;
    private double minimumCost;
    private int addressId;
    private String profilePic;
    private String workingHours;
    private boolean cardAccepted;
    private String Description;
    private String restaurantType;
    private Integer brandId;
    private Location location;
    private Time openingTime;
    private Time closingTime;
    private boolean opened;
    private double avgMealCost;
    private long deliveryTime;
    private String addressDesc;
    private double averageDeliveryTime;

}
