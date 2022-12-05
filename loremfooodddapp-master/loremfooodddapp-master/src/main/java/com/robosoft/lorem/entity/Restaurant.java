package com.robosoft.lorem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer restaurantId;
    private String restaurantName;
    private int userId;
    private Integer overAllRating;
    private double minimumCost;
    private int addressId;
    private MultipartFile profilePic;
    private String profilePicLink;
    private String workingHours;
    private boolean cardAccepted;
    private String Description;
    private String restaurantType;
    private Integer brandId;
    private String area;
    private String city;
    private String addressDesc;
    private double lattitude;
    private double longitude;

}
