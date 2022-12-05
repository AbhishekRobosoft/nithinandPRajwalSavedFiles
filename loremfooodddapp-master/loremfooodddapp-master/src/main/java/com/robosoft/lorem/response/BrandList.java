package com.robosoft.lorem.response;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class BrandList
{
    private int brandId;
    private String brandName;
    private String description;
    private String logo;
    private String profilePic;
    private String brandOrigin;
}
