package com.robosoft.lorem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Data
@RequiredArgsConstructor
public class ReviewInfo
{
    private int reviewId;
    private int userId;
    private int restaurantId;
    private String description;
    private Date date;
    private int foodRating;
    private int serviceRating;
    private int averageRating;
    private List<MultipartFile> multipartFileList;
    private List<String> photoLinks;
    private int orderId;
}
