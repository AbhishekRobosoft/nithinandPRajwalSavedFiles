package com.robosoft.lorem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
public class Menu {
    private int restaurantId;
    private int dishId;
    private float price;
    private boolean customizable;
    private MultipartFile dishPhoto;
    private String dishPhotoLink;
    private String foodType;
    List<Integer> addonIds;
}
