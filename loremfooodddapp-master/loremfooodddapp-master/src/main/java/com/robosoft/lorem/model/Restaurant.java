package com.robosoft.lorem.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Restaurant
{
    private int restaurantId;
    private String restaurantName;
    private int overAllRating;
    private double minimumCost;
    private MultipartFile profilePic;
    private String workingHours;
    private boolean cardAccepted;
    private String description;
    private String restaurantType;
    private int brandId;
}
