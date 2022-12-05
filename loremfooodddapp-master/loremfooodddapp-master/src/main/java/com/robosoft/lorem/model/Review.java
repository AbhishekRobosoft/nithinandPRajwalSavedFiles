package com.robosoft.lorem.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Review
{
    private int reviewId;
    private int userId;
    private int restaurantId;
    private String description;
    private int likeCount;
    private Date date;
    private int foodRating;
    private int serviceRating;
    private int averageRating;
    private int orderId;
}
