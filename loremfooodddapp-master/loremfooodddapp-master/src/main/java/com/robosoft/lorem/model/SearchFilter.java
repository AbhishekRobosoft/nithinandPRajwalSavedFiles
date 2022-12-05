package com.robosoft.lorem.model;

import com.robosoft.lorem.routeResponse.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchFilter {

    private String restaurantOrFoodType;

    private Date date;

    private String address;

    private Location location;

    private int pageNumber;

    private int limit;

    private boolean openNow;

    private double maxAvgMealCost;

    private double maxMinOrderCost;

    private String cuisineType;

    private int deliveryTime;

    private boolean descRating;

    private int brandId;

}
