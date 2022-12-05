package com.robosoft.lorem.model;

import lombok.Data;

@Data
public class AddressDetails {
    private int addressId;
    private boolean primaryAddress;
    private String addressType;
    private String addressDesc;
    private String city;
    private String area;
}
