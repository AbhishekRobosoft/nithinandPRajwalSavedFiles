package com.robosoft.lorem.model;

import lombok.Data;

import java.util.List;

@Data
public class MenuItem {

    private String dishType;
    private List<MenuDetails> menuDetailsList;
    private int count;

    public MenuItem(String dishType) {
        this.dishType = dishType;
    }
}
