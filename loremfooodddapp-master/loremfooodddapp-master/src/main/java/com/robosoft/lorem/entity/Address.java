package com.robosoft.lorem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Address {
    private int addressId;
    private int userId;
    private boolean primaryAddress;
    private String addressType;
    private String city;
    private String area;
    private String addressDesc;
    private double lattitude;
    private double longitude;
}
