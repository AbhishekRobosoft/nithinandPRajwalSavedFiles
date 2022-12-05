package com.robosoft.lorem.model;

import lombok.Data;

@Data
public class BrandDesc {

    private int brandId;
    private String brandName;
    private String description;
    private String logo;
    private String brandOrigin;
    private Double averageDeliveryTime;
    private Double minimumCost;

}
