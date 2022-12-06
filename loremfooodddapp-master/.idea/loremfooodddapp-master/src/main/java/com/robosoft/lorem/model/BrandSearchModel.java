package com.robosoft.lorem.model;

import lombok.Data;

@Data
public class BrandSearchModel {

    private int brandId;
    private String brandName;
    private String description;
    private String logo;
    private String profilePic;
    private String brandOrigin;
}
