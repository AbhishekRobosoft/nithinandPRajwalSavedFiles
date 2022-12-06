package com.robosoft.lorem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer dishId;
    private String dishName;
    private String description;
    private String dishType;
    private boolean veg;
}
