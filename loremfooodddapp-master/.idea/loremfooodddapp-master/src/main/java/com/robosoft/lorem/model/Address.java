package com.robosoft.lorem.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Address
{
    private int addressId;
    private boolean primaryAddress;
    private String addressType;
    private String city;
    private String area;
    private String addressDescription;
    private double lattitude;;
    private double longitude;
    private int userId;
}
