package com.robosoft.lorem.model;

import com.robosoft.lorem.entity.Addon;
import com.robosoft.lorem.entity.Addon;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class MenuDetails {

    private int dishId;
    private String dishName;
    private float price;
    private boolean customizable;
    private String description;
    private String dishPhoto;
    private boolean veg;
    List<Addon> addonList;
    private String dishType;
    private boolean bestSeller;

}
