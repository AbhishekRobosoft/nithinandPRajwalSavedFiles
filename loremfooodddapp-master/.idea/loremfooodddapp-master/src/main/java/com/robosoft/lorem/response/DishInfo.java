package com.robosoft.lorem.response;
import lombok.Data;

import java.util.List;

@Data
public class DishInfo
{
    private int dishId;
    private String dishName;
    private int count;
    private int price;
    private List<AddonInfo> addonInfoList;
    private int addOnCount;
    private Boolean veg;
}
