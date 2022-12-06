package com.robosoft.lorem.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.robosoft.lorem.entity.Addon;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemModel {

    private int dishId;
    private String dishName;
    private String customizationInfo;
    private boolean customizable;
    private int addOnCount;
    private int itemCount;
    private boolean veg;
    private double price;
    private List<Addon> addOns;
    private String itemProfilePic;

}
