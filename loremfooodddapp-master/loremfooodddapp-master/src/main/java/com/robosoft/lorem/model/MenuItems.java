package com.robosoft.lorem.model;

import lombok.Data;

import java.util.List;

@Data
public class MenuItems {
    private int restaurantId;
    private List<MenuItem> menuItem;
}
