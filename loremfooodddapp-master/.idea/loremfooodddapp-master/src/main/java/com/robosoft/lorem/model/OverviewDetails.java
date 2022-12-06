package com.robosoft.lorem.model;

import lombok.Data;

import java.util.List;

@Data
public class OverviewDetails {
    private int restaurantId;
    private String Description;
    private String restaurantType;
    private Double averageCost;
    private boolean cardAccepted;
    private String mobileNo;
    private String addressDesc;
    List<OpeningDetails> openingDetails;
    private double lattitude;
    private double longitude;
}
